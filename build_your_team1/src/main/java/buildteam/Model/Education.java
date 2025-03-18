package buildteam.Model;
import javax.persistence.*;

@Entity
@Table(name = "education")
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Eid;

    @Enumerated(EnumType.STRING) // This tells Hibernate to store it as a String in the database
    private EducationLevel educationLevel; 

    public Long getEid() {
		return Eid;
	}

	public void setEid(Long eid) {
		Eid = eid;
	}

	public Education() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Education [Eid=" + Eid + ", educationLevel=" + educationLevel + ", status=" + status + ", percentage="
				+ percentage + ", passingYear=" + passingYear + ", profile=" + profile + "]";
	}

	public Education(Long eid, EducationLevel educationLevel, String status, int percentage, int passingYear,
			Profile profile) {
		super();
		Eid = eid;
		this.educationLevel = educationLevel;
		this.status = status;
		this.percentage = percentage;
		this.passingYear = passingYear;
		this.profile = profile;
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

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
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

	private String status; // Completed, Pursuing
    private int percentage;
    private int passingYear;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "Pid")
    private Profile profile;

    // Getters and Setters
}

