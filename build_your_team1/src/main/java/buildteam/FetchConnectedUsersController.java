package buildteam;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import buildteam.Dao.FetchConnectDao;
import buildteam.Dao.ProfileDao;
import buildteam.Dao.UserDao;
import buildteam.Model.Users;

@Controller
public class FetchConnectedUsersController {
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private ProfileDao profileDao;
	
	@Autowired
	private FetchConnectDao fetchConnectDao;
	
	
	
	@GetMapping(value = "/acceptedList", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<UserDTO>> getAcceptedUsersWithSkills(HttpSession session) {
		
		System.out.println("inside of getAcceptedUsersWithSkills");
	    int loggedInUserId = (int) session.getAttribute("UserId");
	    List<Users> users = fetchConnectDao.getAcceptedUsersWithProfilesAndSkills(loggedInUserId);

	    List<UserDTO> userDTOs = users.stream()
	        .map(UserDTO::new)
	        .collect(Collectors.toList());

	    return ResponseEntity.ok(userDTOs);
	}
	
	@GetMapping(value = "/RejectedList", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<UserDTO>> getPendingUsersWithSkills(HttpSession session) {
		
		System.out.println("inside of getAcceptedUsersWithSkills");
	    int loggedInUserId = (int) session.getAttribute("UserId");
	    List<Users> users = fetchConnectDao.getPendingUsers(loggedInUserId);

	    List<UserDTO> userDTOs = users.stream()
	        .map(UserDTO::new)
	        .collect(Collectors.toList());

	    return ResponseEntity.ok(userDTOs);
	}

}
