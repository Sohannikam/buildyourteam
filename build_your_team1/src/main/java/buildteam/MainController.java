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
		System.out.println("inside login");
		return "login"; 
	}

@RequestMapping("/ProfileForOtherUser")

public String profileuser()
{
	System.out.println("inside login");
	return "ProfileForOtherUser"; 
}

@RequestMapping("/dashboard")

public String Home()
{
	System.out.println("inside Dashboard");
	return "Dashboard"; 
}
@RequestMapping("/profile")

public String profile()
{
	System.out.println("inside Profile");
	return "profile"; 
}
@RequestMapping("/findpart")

public String findpart()
{
	System.out.println("inside findpart");
	return "Findyourpartner"; 
}
@RequestMapping("/Home")

public String Home1()
{
	System.out.println("inside Home1");
	return "Home"; 
}
@RequestMapping("/Mynet")

public String Mynetork()
{
	System.out.println("inside Mynetwork");
	return "MyNetwork"; 
}

@RequestMapping("/ADDPOST")

public String AddPost()
{
	System.out.println("inside Addpost");
	return "AddPost"; 
}


}
