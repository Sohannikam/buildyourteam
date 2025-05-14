package buildteam;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import buildteam.Dao.SearchDao;
import buildteam.Dao.SkillDao;
import buildteam.Dao.UserDao;
import buildteam.Model.Profile;
import buildteam.Model.Skill;
import buildteam.Model.Users;
import buildteam.Service.ProfileService;
import buildteam.Service.SkillService;

@RestController
public class SearchController {
	
	
	    @Autowired
	    private ProfileService profileService;
	    
	    @Autowired
	    private SearchDao searchDao;
	   
	    
	
	    
	    @GetMapping(value = "/SearchUsers", produces = MediaType.APPLICATION_JSON_VALUE)
	    @ResponseBody
	    public ResponseEntity<List<UserDTO>> getUsers(HttpSession session, @RequestParam("searchBox") String searchBox) {
	    	
	    	try {
	    		
	    		System.out.println("inside of search controller");
	    	 Users user = (Users) session.getAttribute("user");
	    	 
	    	 
	         System.out.println(user);
	         if (user == null) {
	         	System.out.println("inside user==null");
	             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	         }

	         // Get Profile based on logged-in user
	         Profile profile = profileService.getProfileByUser(user);
	         if (profile == null) {
	         	 System.out.println("inside profile==null");
	             return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	            
	         }
	         
	         List<Users> useduserd = searchDao.findUsersBySearch(searchBox);
	         
	         System.out.println(useduserd);
	         
	         List<UserDTO> userDTOs = useduserd.stream()
	    	         .map(user1 -> new UserDTO(user1))
	    	         .collect(Collectors.toList());
	       
	        return ResponseEntity.ok(userDTOs);
	    
	    }
	    

	    
	    	catch (Exception e) {
	    		e.printStackTrace();
	    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
			}
	    
}
}
