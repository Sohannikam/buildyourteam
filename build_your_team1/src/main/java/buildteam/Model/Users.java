package buildteam.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	
	public Users(int id, String email, String name, String pass, String cpass, long phone, String gen) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.pass = pass;
		this.cpass = cpass;
		this.phone = phone;
		this.gen = gen;
	}
	@Override
	public String toString() {
		return "Users [id=" + id + ", email=" + email + ", name=" + name + ", pass=" + pass + ", cpass=" + cpass
				+ ", phone=" + phone + ", gen=" + gen + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Users() {
		super();
		// TODO Auto-generated constructor stub
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
	
}