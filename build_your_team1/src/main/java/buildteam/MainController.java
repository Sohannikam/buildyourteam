package buildteam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping("/Signup")
	
	public String Signup()
	{
		System.out.println("inside Signup");
		return "Signup"; 
	}
	
@RequestMapping("/login")
	
	public String login()
	{
		System.out.println("inside Signup");
		return "login"; 
	}
@RequestMapping("/dashboard")

public String Home()
{
	System.out.println("inside Signup");
	return "Home"; 
}
//@RequestMapping("/Signup")
//
//public String Signup()
//{
//	System.out.println("inside Signup");
//	return "Signup"; 
//}
//@RequestMapping("/Signup")
//
//public String Signup()
//{
//	System.out.println("inside Signup");
//	return "Signup"; 
//}
//@RequestMapping("/Signup")
//
//public String Signup()
//{
//	System.out.println("inside Signup");
//	return "Signup"; 
//}
//@RequestMapping("/Signup")
//
//public String Signup()
//{
//	System.out.println("inside Signup");
//	return "Signup"; 
//}

}
