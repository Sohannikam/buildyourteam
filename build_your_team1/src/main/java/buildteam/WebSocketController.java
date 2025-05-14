package buildteam;

import java.nio.file.Files;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import buildteam.Dao.MessageDao;
import buildteam.Dao.UserDao;
import buildteam.Model.Message;
import buildteam.Model.Message.MessageStatus;
import buildteam.Model.Users;

@Controller
public class WebSocketController {

	    @Autowired 
	    private SimpMessagingTemplate messagingTemplate;

	    @Autowired
	    private UserDao userDao;

	    @Autowired
	    private MessageDao messageDao;
	    
	    @Autowired
	    private ObjectMapper objectMapper;
	    
	 


	    @MessageMapping("/chat.sendMessage")
	    public void sendMessage(@Payload MessageDto messageDTO) {

	        System.out.println("inside sendMessage WebSocket controller");

	        Users sender = userDao.getUserById(messageDTO.getSenderId());
	        Users receiver = userDao.getUserById(messageDTO.getReceiverId());

	        if (sender == null || receiver == null) {
	            System.out.println("either sender is null or receiver is null");
	            return; // Stop execution if sender or receiver is invalid
	        }

	        // Create and save message
	        Message message = new Message();
	        message.setMsender(sender);
	        message.setMreceiver(receiver);
	        message.setMessageText(messageDTO.getMessageText());
	        message.setFilePath(messageDTO.getFilePath());
	        message.setTimestamp(LocalDateTime.now());
	        message.setStatus(MessageStatus.SENT);

	        messageDao.saveMessage(message);

	        // Convert Message entity to MessageDto to avoid recursion issue
	        MessageDto responseDto = new MessageDto();
	        responseDto.setId(message.getId());
	        responseDto.setSenderId(sender.getId());
	        responseDto.setReceiverId(receiver.getId());
	        responseDto.setMessageText(message.getMessageText());
	        responseDto.setFilePath(message.getFilePath());
	        responseDto.setTimestamp(message.getTimestamp());
	        responseDto.setStatus(message.getStatus().toString());

	        // ✅ Send message DTO to the receiver's private topic
	        messagingTemplate.convertAndSend("/topic/messages/" + receiver.getId(), responseDto);
	        messagingTemplate.convertAndSend("/topic/messages/" + sender.getId(), responseDto);

	    }

	    
	    
	    @GetMapping("/chat")
	    @ResponseBody
	    public ResponseEntity<List<MessageDto>> getChatMessages(@RequestParam int senderId, @RequestParam int receiverId) {
	    	
	    	System.out.println("inside fetchmessage controller");
	        List<Message> messages = messageDao.getMessagesBetweenUsers(senderId, receiverId);
	        
	        List<MessageDto> messageDTOs = messages.stream().map(message -> new MessageDto(
	        	    message.getId(),
	        	    message.getMsender().getId(),   // senderId
	        	    message.getMreceiver().getId(), // receiverId
	        	    message.getMessageText(),
	        	    message.getFilePath(),
	        	    message.getTimestamp(),
	        	    message.getStatus().toString()
	        	)).collect(Collectors.toList());

	        return ResponseEntity.ok(messageDTOs);
	    }
	    
	   
	    
	    
	    private static final String UPLOAD_DIR = "C:/Users/sohan/Documents/Uploads/";
	    
	    @PostMapping("/uploadFile")
	    @ResponseBody
	    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
	        if (file.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
	        }

	        try {
	            // Save the file
	            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
	            Path filePath = Paths.get(UPLOAD_DIR + fileName);
	            Files.copy(file.getInputStream(), filePath);

	            // Return file URL
	            return ResponseEntity.ok(Map.of("filePath", fileName));
	        } catch (IOException e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
	        }
	    }

	    
	    @PostMapping(value="/updateStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	    @ResponseBody
	    public ResponseEntity<?> updateMessageStatus(@RequestBody Map<String, Object> requestData) {
	    	
	    	System.out.println("inside updatestatus");
	    	int messageId = Integer.parseInt(requestData.get("messageId").toString());
	        String newStatus = requestData.get("status").toString();
	        
	        System.out.println(newStatus);

	        Message message = messageDao.getMessageById(messageId);
	        if (message == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message not found");
	        }

	        // Update status
	        if ("DELIVERED".equals(newStatus)) {
	        	System.out.println("inside delivered");
	            message.setStatus(MessageStatus.DELIVERED);
	        } else if ("SEEN".equals(newStatus)) {
	        	System.out.println("inside seen");
	            message.setStatus(MessageStatus.SEEN);
	        }

	        messageDao.updateMessage(message);
	        
	        // Convert Message entity to MessageDto to avoid recursion
	        MessageDto messageDto = new MessageDto(
	            message.getId(),
	            message.getMsender().getId(),  // Sender ID
	            message.getMreceiver().getId(),  // Receiver ID
	            message.getMessageText(),
	            message.getFilePath(),
	            message.getTimestamp(),
	            message.getStatus().toString()  // Convert Enum to String if needed
	        );

	        // Notify sender that message status has changed
	        messagingTemplate.convertAndSend("/topic/messages/status/" + message.getMsender().getId(), messageDto);

	        return ResponseEntity.ok(messageDto);
	    }
	    
	    
}

