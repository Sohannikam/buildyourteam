package buildteam;

import java.time.LocalDateTime;

public class MessageDto {
	  private int id;
	  private int senderId;
	    private int receiverId;
	    private String messageText;
	    private String filePath;
	    private LocalDateTime timestamp;
	    private String status;
	    
	    
	    
	    

	    public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public LocalDateTime getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		// Getters and Setters
	    public int getSenderId() { return senderId; }
	    public void setSenderId(int senderId) { this.senderId = senderId; }

	    public int getReceiverId() { return receiverId; }
	   
		public void setReceiverId(int receiverId) { this.receiverId = receiverId; }

	    public String getMessageText() { return messageText; }
	    public void setMessageText(String messageText) { this.messageText = messageText; }
	    
	    public String getFilePath() {
			return filePath;
		}
		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}
		public MessageDto(int id, int senderId, int receiverId, String messageText, String filePath,
				LocalDateTime timestamp, String status) {
			super();
			this.id = id;
			this.senderId = senderId;
			this.receiverId = receiverId;
			this.messageText = messageText;
			this.filePath = filePath;
			this.timestamp = timestamp;
			this.status = status;
		}
		public MessageDto() {
			super();
			// TODO Auto-generated constructor stub
		}
	
		
		
		
		
}
