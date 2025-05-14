package buildteam.Model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "Requests")
public class Request {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	
		@ManyToOne
	    @JoinColumn(name = "sender_id", nullable = false)
		@JsonIgnore
	    private Users sender;

	    @ManyToOne
	    @JoinColumn(name = "receiver_id", nullable = false)
	    @JsonIgnore
	    private Users receiver;

	    public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Users getSender() {
			return sender;
		}

		public void setSender(Users sender) {
			this.sender = sender;
		}

		public Users getReceiver() {
			return receiver;
		}

		public void setReceiver(Users receiver) {
			this.receiver = receiver;
		}

		public RequestStatus getStatus() {
			return status;
		}

		public void setStatus(RequestStatus status) {
			this.status = status;
		}

		@Column(nullable = false)
	    @Enumerated(EnumType.STRING)
	    private RequestStatus status;
	    
	    public Request() {
			super();
			// TODO Auto-generated constructor stub
		}

			public Request( Users sender, Users receiver, RequestStatus status) {
			super();
			
			this.sender = sender;
			this.receiver = receiver;
			this.status = status;
		}

}
