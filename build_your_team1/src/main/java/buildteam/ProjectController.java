package buildteam;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import buildteam.Dao.ProjectDao;
import buildteam.Dao.SkillDao;
import buildteam.Model.Profile;
import buildteam.Model.Project;
import buildteam.Model.Skill;
import buildteam.Model.Users;
import buildteam.Service.ProfileService;
import buildteam.Service.SkillService;

@Controller
public class ProjectController {


	    @Autowired
	    private ProfileService profileService;
	    
	    @Autowired
	    private UserHandler userhand;
	    
	    @Autowired
	    private ProjectDao projectDao;
	    
	   
	    
	    @GetMapping(value = "/getProjects", produces = MediaType.APPLICATION_JSON_VALUE)
	    @ResponseBody
	    public ResponseEntity<List<Project>> getProjects( HttpSession session) {
	    	try {
	        // Retrieve the logged-in user from session
	    	
	    	 System.out.println("Inside Project Controller");
	    	 
	    	 
	    	System.out.println("inside ResponseEntity of Skill controller");
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
	            
	            // Fetch updated skill list
	            List<Project> updatedProectList = projectDao.findByProfile(profile);
	        
//	        System.out.println(updatedProectList);
	        return ResponseEntity.ok(updatedProectList);
	    }
	    	catch (Exception e) {
	    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
			}
	    }

	    
	    //add project in database
	    @PostMapping(value="/addProject", produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<List<Project>> addProject(@RequestParam String projectName,@RequestParam String projectDescription, HttpSession session) {
	    	try {
	        // Retrieve the logged-in user from session
	    	
	    	 System.out.println("Inside Project Controller");
	    	 
	    	 System.out.println("project name"+projectName);
	    	 System.out.println("project description"+projectDescription);
	    	 
	    	System.out.println("inside ResponseEntity of Skill controller");
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

	            Project project = new Project();
	            project.setProjectName(projectName);
	            project.setDescription(projectDescription);
	            project.setProfile(profile);
	            projectDao.save(project);
	            
	            // Fetch updated skill list
	            List<Project> updatedProectList = projectDao.findByProfile(profile);
	        
//	        System.out.println(updatedProectList);
	        return ResponseEntity.ok(updatedProectList);
	    }
	    	catch (Exception e) {
	    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
			}
	    }
}
