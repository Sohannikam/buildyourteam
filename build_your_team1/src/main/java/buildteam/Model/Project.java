package buildteam.Model;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Proid;

	private String projectName;
	
	  @Lob
	  @Column(columnDefinition = "TEXT")
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "Pid")
    @JsonIgnore // 👈 Prevent infinite loop
    private Profile profile;

    public Long getProid() {
		return Proid;
	}

	public void setProid(Long proid) {
		Proid = proid;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDescription() {
		return description;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

    // Getters and Setters
    
    @Override
	public String toString() {
		return "Project [Proid=" + Proid + ", projectName=" + projectName + ", description=" + description
				+ ", profile=" + profile + "]";
	}
    
    public Project(Long proid, String projectName, String description, Profile profile) {
		super();
		Proid = proid;
		this.projectName = projectName;
		this.description = description;
		this.profile = profile;
    }
    
    public Project() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	

}

