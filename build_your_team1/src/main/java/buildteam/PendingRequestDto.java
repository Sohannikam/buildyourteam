package buildteam;

public class PendingRequestDto {
	
	private int id;
	private String status;
	private int senderId;
//	private String senderName;
	private int receiverId;
//	private String receiverName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public int getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}
	
	public PendingRequestDto(int id, String status, int senderId, int receiverId) {
		super();
		this.id = id;
		this.status = status;
		this.senderId = senderId;
		this.receiverId = receiverId;
	}
	
	public PendingRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
