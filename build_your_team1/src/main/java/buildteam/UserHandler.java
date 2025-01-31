package buildteam;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import buildteam.Dao.UserDao;
import buildteam.Model.Users;



@Controller
public class UserHandler {
	
	@Autowired
	private UserDao ud;
	
	//handle User Sign in
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String handleProduct(@ModelAttribute Users use, Model m) {
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
	        m.addAttribute("message", "User registered successfully!");
	        m.addAttribute("alertType", "success");
	        return "redirect:login";
	        
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
		public RedirectView loginuser(@ModelAttribute Users use,Model m,RedirectAttributes redirectAttributes) {
		
			
				Users user=ud.getUsers(use.getEmail(), use.getPass());
			
			// Add a success message
				if(user!=null)
				{
					System.out.println("inside succesesfull login");
					redirectAttributes.addFlashAttribute("loginuser",user);
		              return new RedirectView("dashboard");
				}
				else 
					{
					System.out.println("inside error page");
						redirectAttributes.addFlashAttribute("error","Invalid email or password");
				        return new RedirectView("login");
					}
			
		}
		

	}


 
