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

    public UserDTO(Users user) {
        this.id = user.getId();
        this.name = user.getName();

        Profile profile = user.getProfile();
        if (profile != null) {
        	
        	
            this.skills = profile.getSkills() != null
                ? profile.getSkills().stream().map(Skill::getSkillName).collect(Collectors.toList())
                : List.of();
        } else {
            
            this.skills = List.of();
        }
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public List<String> getSkills() { return skills; }
}
