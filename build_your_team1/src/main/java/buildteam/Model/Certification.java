package buildteam.Model;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "certifications")
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Cid;

	private String filePath; // Store file path in DB

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "Pid")
    @JsonIgnore // 👈 Prevent infinite loop
    private Profile profile;
    
    private String CertifcateName;

    public String getCertifcateName() {
		return CertifcateName;
	}

	public void setCertifcateName(String certifcateName) {
		CertifcateName = certifcateName;
	}

	public Long getCid() {
		return Cid;
	}

	public void setCid(Long cid) {
		Cid = cid;
	}
	public Certification() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Certification(Long cid, String filePath, Profile profile,String CertificateName) {
		super();
		Cid = cid;
		this.filePath = filePath;
		this.profile = profile;
		this.CertifcateName=CertificateName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "Certification [Cid=" + Cid + ", filePath=" + filePath + ", profile=" + profile + ", CertifcateName="
				+ CertifcateName + "]";
	}

    
}
