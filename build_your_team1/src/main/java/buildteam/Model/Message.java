package buildteam.Model;
import java.sql.Date;


import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "Msender_id", nullable = false)
    @JsonBackReference

    private Users Msender;

    @ManyToOne
    @JoinColumn(name = "Mreceiver_id", nullable = false)
    @JsonBackReference

    private Users Mreceiver;

    @Column(name = "message_text", length = 3000)
    private String messageText;

    @Column(name = "file_path")
    private String filePath;  // Stores file location

    @Column(name = "timestamp", nullable = false)

    private LocalDateTime timestamp;
    
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    public enum MessageStatus {
        SENT, DELIVERED, SEEN
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Users getMsender() {
		return Msender;
	}

	public void setMsender(Users msender) {
		Msender = msender;
	}

	public Users getMreceiver() {
		return Mreceiver;
	}

	public void setMreceiver(Users mreceiver) {
		Mreceiver = mreceiver;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public MessageStatus getStatus() {
		return status;
	}

	public void setStatus(MessageStatus status) {
		this.status = status;
	}

	public Message(int id, Users msender, Users mreceiver, String messageText, String filePath, LocalDateTime timestamp,
			MessageStatus status) {
		super();
		this.id = id;
		Msender = msender;
		Mreceiver = mreceiver;
		this.messageText = messageText;
		this.filePath = filePath;
		this.timestamp = timestamp;
		this.status = status;
	}

	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", messageText=" + messageText + ", filePath=" + filePath + ", timestamp="
				+ timestamp + ", status=" + status + "]";
	}

    // Getters and Setters
	
	
    
    
}