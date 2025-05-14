package buildteam;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import buildteam.Dao.UserFilterDao;
import buildteam.Model.Users;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;

@Controller
public class ApplyFilterController {
	

	 @Autowired
	    private SessionFactory sessionFactory;
	 
	 @Autowired
		private HibernateTemplate hbt;
	 
	 @Autowired
	 private UserFilterDao userFilterDao;
	 

	 
	 @PostMapping(value = "/filterUsers", produces = MediaType.APPLICATION_JSON_VALUE)
	 @ResponseBody
	 public ResponseEntity<List<UserDTO>> filterUsers(
	         @RequestBody FilterRequestDto filterRequest,
	         HttpSession session) {

	     System.out.println("Inside filterUsers Controller");
	     System.out.println("Location: " + filterRequest.getLocation());
	     System.out.println("Experience: " + filterRequest.getExperience());
	     System.out.println("Skills: " + filterRequest.getSkills());

	     Users user = (Users) session.getAttribute("user");
	     if (user == null) {
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	     }

	     List<Users> filteredUsers = userFilterDao.findUsersByFilters(
	         filterRequest.getLocation(), 
	         filterRequest.getSkills(), 
	         filterRequest.getExperience()
	     );

	     List<UserDTO> userDTOs = filteredUsers.stream()
	         .map(user1 -> new UserDTO(user1))
	         .collect(Collectors.toList());

	     return ResponseEntity.ok(userDTOs);
	 }




}
