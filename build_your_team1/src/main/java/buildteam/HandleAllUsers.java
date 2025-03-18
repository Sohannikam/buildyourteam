package buildteam;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import buildteam.Dao.ProfileDao;
import buildteam.Dao.UserDao;
import buildteam.Model.Profile;
import buildteam.Model.Skill;
import buildteam.Model.Users;

@Controller
public class HandleAllUsers {


@Autowired
private UserDao userDao;

@Autowired
private ProfileDao profileDao;


@GetMapping(value = "/userslist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getAllUsersWithSkills(HttpSession session) {
	
	System.out.println("inside getalluserswithskllls handler");
	int loggedInUserId = (int) session.getAttribute("UserId");
        List<Users> users = userDao.getAllUsersWithProfilesAndSkills(loggedInUserId);
      


        // Convert Users entity to DTO format to avoid exposing sensitive data
        List<UserDTO> userDTOs = users.stream().map(user -> new UserDTO(user)).collect(Collectors.toList());

        return ResponseEntity.ok(userDTOs);
    }
    
    @GetMapping(value = "/displayImage1/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> displayProfilePicture(@PathVariable int userId) {
    	
    	System.out.println("inside displaay profile pic of all users");
        Users user = userDao.findUserById(userId);

        if (user == null) {
        	
        	System.out.println("user is null for profile pictuer with id id");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"image\": null}");
        }

        Profile profile = profileDao.findByUser(user);

        if (profile != null && profile.getProfilePicture() != null) {
        	
        	System.out.println("profile pictuers for all users fetched rightly");
            String base64Image = Base64.getEncoder().encodeToString(profile.getProfilePicture());
            return ResponseEntity.ok("{\"image\":\"" + base64Image + "\"}");
        } else {
        	
        	System.out.println("profile image of all  user is null");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"image\": null}");
        }
    }

}
