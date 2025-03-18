package buildteam.Model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Pid;

	@OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")//Foreign key in profile table named as user_Pid
    private Users user;  // One-to-One with User

	    @Lob
	    @Column(name = "profile_picture", columnDefinition = "BLOB")
	    private byte[] profilePicture;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    
    private List<Education> educations;  // One-to-Many with Education

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)//one profile can have multiple skills
    private List<Skill> skills;  // One-to-Many with Skills

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;  // One-to-Many with Projects

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certification> certifications;  // One-to-Many with Certifications

	public Long getPid() {
		return Pid;
	}

	public void setPid(Long Pid) {
		this.Pid = Pid;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	  public byte[] getProfilePicture() {
	        return profilePicture;
	    }

	    public void setProfilePicture(byte[] profilePicture) {
	        this.profilePicture = profilePicture;
	    }


	public List<Education> getEducations() {
		return educations;
	}

	public void setEducations(List<Education> educations) {
		this.educations = educations;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<Certification> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<Certification> certifications) {
		this.certifications = certifications;
	}

	public Profile(Long Pid, Users user, byte[] profilePicture, List<Education> educations, List<Skill> skills,
			List<Project> projects, List<Certification> certifications) {
		super();
		this.Pid = Pid;
		this.user = user;
		this.profilePicture = profilePicture;
		this.educations = educations;
		this.skills = skills;
		this.projects = projects;
		this.certifications = certifications;
	}

	public Profile() {
		super();
		// TODO Auto-generated constructor stub
	}

    // Constructors, Getters, and Setters
    @Override
    public String toString() {
        return "Profile [Pid=" + Pid + ", user=" + user + ", educations=" + educations + ", skills=" + skills +
               ", projects=" + projects + ", certifications=" + certifications + "]";
    }
}

