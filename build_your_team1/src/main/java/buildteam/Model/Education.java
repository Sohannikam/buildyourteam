package buildteam.Model;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "education")
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Eid;
    
    private String status; // Completed, Pursuing
    private double percentage;
    private int passingYear;
    private String Branch;

    public String getBranch() {
		return Branch;
	}

	public void setBranch(String branch) {
		Branch = branch;
	}

	@ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "Pid")
    @JsonIgnore
    private Profile profile;

    // Getters and Setters

    @Enumerated(EnumType.STRING) // This tells Hibernate to store it as a String in the database
    private EducationLevel educationLevel; 

    public Long getEid() {
		return Eid;
	}

	public void setEid(Long eid) {
		Eid = eid;
	}


	
	public EducationLevel getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(EducationLevel educationLevel) {
		this.educationLevel = educationLevel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage2) {
		this.percentage = percentage2;
	}

	public int getPassingYear() {
		return passingYear;
	}

	public void setPassingYear(int passingYear) {
		this.passingYear = passingYear;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
		

	@Override
	public String toString() {
		return "Education [Eid=" + Eid + ", status=" + status + ", percentage=" + percentage + ", passingYear="
				+ passingYear + ", Branch=" + Branch + ", profile=" + profile + ", educationLevel=" + educationLevel
				+ "]";
	}

	public Education(Long eid, String status, double percentage, int passingYear, String branch, Profile profile,
			EducationLevel educationLevel) {
		super();
		Eid = eid;
		this.status = status;
		this.percentage = percentage;
		this.passingYear = passingYear;
		Branch = branch;
		this.profile = profile;
		this.educationLevel = educationLevel;
	}

	public Education() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	 @JsonGetter("formattedEducationLevel")
	    public String getFormattedEducationLevel() {
	        return educationLevel.getDisplayName(); // Calls the method from Enum
	    }

	
}

