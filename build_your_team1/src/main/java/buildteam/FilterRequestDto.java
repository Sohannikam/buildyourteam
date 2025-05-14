package buildteam;

import java.util.List;

public class FilterRequestDto {
    private String location;
    private List<String> skills;
    private String experience;
    private String username;

    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	// Getters and Setters
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getSkills() {
        return skills;
    }
    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getExperience() {
        return experience;
    }
    public void setExperience(String experience) {
        this.experience = experience;
    }
}
