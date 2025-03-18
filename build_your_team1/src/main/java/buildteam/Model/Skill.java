package buildteam.Model;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "skills")
public class Skill {
    @Override
	public String toString() {
		return "Skill [Sid=" + Sid + ", skillName=" + skillName + ", profile=" + profile + "]";
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Sid;

    public Long getSid() {
		return Sid;
	}

	public void setSid(Long sid) {
		Sid = sid;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	private String skillName;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "Pid")//Foregin key column in post table
    @JsonIgnore // 👈 Prevent infinite loop
    private Profile profile;

	public Skill() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Skill(Long sid, String skillName, Profile profile) {
		super();
		Sid = sid;
		this.skillName = skillName;
		this.profile = profile;
	}

    // Getters and Setters
}

