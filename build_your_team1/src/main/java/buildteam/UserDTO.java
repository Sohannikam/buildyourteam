package buildteam;

import java.util.List;
import java.util.stream.Collectors;
import buildteam.Model.Profile;
import buildteam.Model.Skill;
import buildteam.Model.Users;

public class UserDTO {
    private int id;
    private String name;
    private List<String> skills;
    private String city;
    private int requestId; // Add this
    public UserDTO(Users user) {
        this.id = user.getId();
        this.name = user.getName();
        this.city=user.getCity();
//        this.requestId=requestId;

        Profile profile = user.getProfile();
        if (profile != null) {
        	
        	
            this.skills = profile.getSkills() != null
                ? profile.getSkills().stream().map(Skill::getSkillName).collect(Collectors.toList())
                : List.of();
        } else {
            
            this.skills = List.of();
        }
    }

    public UserDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }


	// Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public List<String> getSkills() { return skills; }
    public String getCity() {return city;}
//    public int getRequestId() {return requestId;}
    
}
