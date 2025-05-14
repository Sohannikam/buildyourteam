package buildteam;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import buildteam.Dao.UserDao;
import buildteam.Model.Profile;
import buildteam.Model.Users;
import buildteam.Service.ProfileService;

@Controller
public class OtherProfileController {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProfileService profileService;
	
	@GetMapping("/loadUserProfile")
	public String loadUserProfile(@RequestParam("userId") int userId, Model model,HttpSession session) {
		
		 Users user = userDao.findUserById(userId);
		    if (user == null) return "error"; // or your error page

	        Profile profile = profileService.getProfileByUser(user);
		    model.addAttribute("otheruser", user);
		    model.addAttribute("profile", profile); // if needed for skills/projects

		    return "ProfileForOtherUser"; // this should resolve to profileforotheruser.jspWill render profileforotheruser.jsp
	}


}
