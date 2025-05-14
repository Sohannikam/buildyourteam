package buildteam;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import buildteam.Dao.UpdateProfileDao;
import buildteam.Model.Profile;
import buildteam.Model.Skill;
import buildteam.Model.Users;
import buildteam.Service.ProfileService;

@RestController
public class UpdateProfileController {

    @Autowired
    private ProfileService profileService;
    
    @Autowired
    private UserHandler userhand;
    
    @Autowired
    private UpdateProfileDao updateProfileDao;
    
    
    

    

    @PutMapping(value="/updateProfile", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> UpdateProfile(
            @RequestBody Map<String, String> requestData, HttpSession session) {
        try {
            System.out.println("inside update profile controller");
             
            Users user = (Users) session.getAttribute("user");
            if (user == null) {
                System.out.println("inside user==null");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            user.setName(requestData.get("name"));
            user.setEmail(requestData.get("email"));
            user.setPhone(Long.parseLong(requestData.get("mobile")));
            user.setGen(requestData.get("gender"));
            user.setState(requestData.get("state"));
            user.setCity(requestData.get("city"));

            updateProfileDao.updateUser(user);
            
            session.setAttribute("user08", user);
            System.out.println("updated email is"+user.getEmail());

            return ResponseEntity.ok("{\"status\": \"success\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"status\": \"error\"}");
        }
    }

	
	
}
