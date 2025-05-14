package buildteam.Model;

import java.util.List;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String email;
	private String name;
	private String pass;
	private String cpass;
	private long phone;

	private String gen;
	private String State;
	private String City;
	
	@OneToMany(mappedBy = "sender" , cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Request> sentRequests;

	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
    private List<Request> receivedRequests;

	 @OneToMany(mappedBy = "Msender", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Message> messagesender;
	 
	 @OneToMany(mappedBy = "Mreceiver" , cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Message> messageReciever;
	 
	 
	
	
	  public List<Message> getMessagesender() {
		return messagesender;
	}




	public void setMessagesender(List<Message> messagesender) {
		this.messagesender = messagesender;
	}




	public List<Message> getMessageReciever() {
		return messageReciever;
	}




	public void setMessageReciever(List<Message> messageReciever) {
		this.messageReciever = messageReciever;
	}




	public List<Request> getSentRequests() {
		return sentRequests;
	}




	public void setSentRequests(List<Request> sentRequests) {
		this.sentRequests = sentRequests;
	}




	public List<Request> getReceivedRequests() {
		return receivedRequests;
	}




	public void setReceivedRequests(List<Request> receivedRequests) {
		this.receivedRequests = receivedRequests;
	}
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL , orphanRemoval = true) // "user" refers to the field in Profile  = mappedBy = "user" → Tells Hibernate that the relationship is already mapped by the user field in Profile, avoiding an extra column.
	    private Profile profile;
	

	
	@Override
	public String toString() {
		return "Users [id=" + id + ", email=" + email + ", name=" + name + ", pass=" + pass + ", cpass=" + cpass
				+ ", phone=" + phone + ", gen=" + gen + ", State=" + State + ", City=" + City +"]";
	}



	public Profile getProfile() {
		return profile;
	}


	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getCpass() {
		return cpass;
	}
	public void setCpass(String cpass) {
		this.cpass = cpass;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public String getGen() {
		return gen;
	}
	public void setGen(String gen) {
		this.gen = gen;
	}
	
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}




	public Users(int id, String email, String name, String pass, String cpass, long phone, String gen, String state,
			String city, List<Request> sentRequests, List<Request> receivedRequests, List<Message> messagesender,
			List<Message> messageReciever, Profile profile) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.pass = pass;
		this.cpass = cpass;
		this.phone = phone;
		this.gen = gen;
		this.State = state;
		this.City = city;
		this.sentRequests = sentRequests;
		this.receivedRequests = receivedRequests;
		this.messagesender = messagesender;
		this.messageReciever = messageReciever;
		this.profile = profile;
	}
	
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}