package buildteam;

import buildteam.Dao.RequestDao;
import buildteam.Dao.UserDao;
import buildteam.Model.Request;
import buildteam.Model.Users;
import buildteam.Model.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class RequestController {

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private UserDao userDao;
    
  

    
    @PostMapping(value="/send/{receiverId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendRequest(@SessionAttribute("UserId") int senderId, @PathVariable int receiverId) {
    	
    	System.out.println("inside send request");
        Users sender = userDao.findUserById(senderId);
        Users receiver = userDao.findUserById(receiverId);

        if (sender == null || receiver == null) {
            return ResponseEntity.badRequest().body("Invalid sender or receiver.");
        }

        requestDao.sendRequest(sender, receiver);
        return ResponseEntity.ok(Map.of("message", "Request Sent"));

    }

   
    @GetMapping(value = "/pending", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PendingRequestDto>> getPendingRequests(@SessionAttribute("UserId") int userId) {
    	
    	
    	System.out.println("Fetching Pending Requests...");
    	List<Request> requests=requestDao.getPendingRequestsForUser(userId);
    	
    	List<PendingRequestDto> pendingRequests= requests.stream().map(request -> new PendingRequestDto(
    				request.getId(),
    				request.getStatus().toString(),
    				request.getSender().getId(),
    				request.getReceiver().getId()
    			)).collect(Collectors.toList());

    	return ResponseEntity.ok(pendingRequests);
    }
    
    //fetch all request from the database
    
    
    @GetMapping(value = "/fetchAllRequests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PendingRequestDto>> getAllRequests() {
    	
    	
    	System.out.println("Fetching Pending Requests...");
    	List<Request> requests=requestDao.getAllRequestForuser();
    	
    	List<PendingRequestDto> pendingRequests1= requests.stream().map(request -> new PendingRequestDto(
    				request.getId(),
    				request.getStatus().toString(),
    				request.getSender().getId(),
    				request.getReceiver().getId()
    			)).collect(Collectors.toList());

    	return ResponseEntity.ok(pendingRequests1);
    }
    
    

    //check status of request for login users
    @GetMapping(value = "/checkStatus/{receiverId1}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Request>> getStausofRequest(@SessionAttribute("UserId") int SenderId,@PathVariable int receiverId1) {
    	
    	System.out.println("inside to check pending reqeust");
        List<Request> requests = requestDao.getStausofRequest(SenderId,receiverId1);
        return ResponseEntity.ok(requests);
    }
    
    
  
    @PutMapping(value = "/update/{requestId}/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRequestStatus(@PathVariable int requestId, @PathVariable String status) {
    	
    	System.out.println("request id is"+requestId);
    	System.out.println("inside to check request status");
        if (!status.equals("Accepted") && !status.equals("Rejected")) {
        	
            return ResponseEntity.badRequest().body("Invalid status.");
        }

        requestDao.updateRequestStatus(requestId, RequestStatus.valueOf(status));
        return ResponseEntity.ok(Map.of("message","Request"+status));
    }
    
    @PutMapping(value = "/Reject/{requestId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRequestStatusReject(@PathVariable int requestId) {
    	
    	System.out.println("request id is"+requestId);
    	System.out.println("inside to check request status");
        
    	int Userid=requestDao.getReceiverIdFromRequestId(requestId);
    	System.out.println("User id for sender is"+Userid);
        requestDao.updateRequestStatusForReject(requestId);
     // Return JSON with userId to handle UI dynamically
    	return ResponseEntity.ok(Map.of("userId",Userid)); // This function will get receiverId from requestId
    }
}
