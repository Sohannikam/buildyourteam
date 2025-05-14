package buildteam;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import buildteam.Dao.ProfileDao;
import buildteam.Dao.UserDao;
import buildteam.Model.Profile;
import buildteam.Model.Users;



@Controller
public class UserHandler {
	
	@Autowired
	private UserDao ud;
	
	@Autowired
	private ProfileDao profileDao;
	
	
	//handle User Sign in
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String handleProduct(@ModelAttribute Users use, Model m,HttpSession session) {
	    try {
	        if (ud.doesEmailExist(use.getEmail())) {
	            m.addAttribute("message", "Email already exists. Please enter a new email.");
	            m.addAttribute("alertType", "danger");
	            m.addAttribute("user", use); // Retain user data
	            
	            return "Signup"; // Stay on signup page
	        }
	        
	        else if(ud.doesPhoneExist(use.getPhone()))
	        {
	        	 m.addAttribute("message", "Phone already exists. Please enter a new Phone NO.");
		            m.addAttribute("alertType", "danger");
		            m.addAttribute("user", use); // Retain user data
		            return "Signup"; // Stay on signup page
	        }
	        
	     
	        ud.createUser(use);
	        m.addAttribute("message1", "User registered successfully!");
	        m.addAttribute("alertType", "success");
	        
	        Users user=ud.getUsers(use.getEmail(), use.getPass());
	        
	        if(user!=null)
	        {
	        	// Inside your SignupController
	        	Profile profile = new Profile();
	        	profile.setUser(user);  // Set the user reference
	        	  // Set default profile pic if needed
	   

	        	profileDao.save(profile);  // Save profile in DB

	        }
	        
	        return "login";
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        m.addAttribute("message", "Registration failed. Please try again.");
	        m.addAttribute("alertType", "danger");
	        m.addAttribute("user", use); // Retain user data
	        return "signup";
	    }
	}

		
		//Handle user login
		@RequestMapping(value="/Login",method = RequestMethod.POST)
		public String loginuser(@ModelAttribute Users use,Model m,RedirectAttributes redirectAttributes,HttpSession session) {
			System.out.println("inside loginuser method");
		
			 // ✅ Get the updated user from the session
//	        Users sessionUser = (Users) session.getAttribute("user08");
//
//	        if (sessionUser != null) {
//	            System.out.println("Using updated user from session: " + sessionUser.getEmail());
//	            use.setEmail(sessionUser.getEmail()); // ✅ Use the updated email
//	        } else {
//	            System.out.println("No user found in session.");
//	        }

			if (!ud.doesEmailExist(use.getEmail()))  {
		            m.addAttribute("message", "Email does not exists");
		            m.addAttribute("alertType", "danger");
		            m.addAttribute("user", use); // Retain user data
		            return "login"; // Stay on signup page
		        }
			
				Users user=ud.getUsers(use.getEmail(), use.getPass());
			
			// Add a success message
			 if(user!=null)
				{
				
			        
					System.out.println("inside succesesfull login");
					m.addAttribute("loginuser",user);
					session.setAttribute("user", user);
					session.setAttribute("UserId", user.getId());
				
			        return "redirect:dashboard";
				}
				else 
					{
					System.out.println("Invalid password");
						m.addAttribute("error","Invalid password");
				        return "login";
					}
			
		}
		

	}


 
