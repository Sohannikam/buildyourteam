package buildteam;

import buildteam.Model.Certification;

public class CertificationDto {
    private Long cid;
    private String certifcateName;
    private String filePath;
    private Long pid; // include Pid directly

    // Constructor
    public CertificationDto(Certification cert) {
        this.cid = cert.getCid();
        this.certifcateName = cert.getCertifcateName();
        this.filePath = cert.getFilePath();
        this.pid = cert.getProfile().getPid();
    }

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getCertifcateName() {
		return certifcateName;
	}

	public void setCertifcateName(String certifcateName) {
		this.certifcateName = certifcateName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}
    
    

    // Getters and setters
    
    
}

