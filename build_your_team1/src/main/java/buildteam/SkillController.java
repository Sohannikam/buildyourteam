package buildteam;

import org.apache.jasper.tagplugins.jstl.core.Catch;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import buildteam.Model.Skill;
import buildteam.Dao.SkillDao;
import buildteam.Model.Profile;
import buildteam.Model.Users;
import buildteam.Service.SkillService;
import buildteam.Service.ProfileService;

import javax.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;

@Controller

public class SkillController {

    @Autowired
    private SkillService skillService;

    @Autowired
    private ProfileService profileService;
    
    @Autowired
    private UserHandler userhand;
    
    @Autowired
    private SkillDao skillDao;
    
    @GetMapping(value = "/getSkills", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Skill>> getSkills(HttpSession session) {
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

        List<Skill> skills = skillService.getSkillsByProfile(profile);
        return ResponseEntity.ok(skills);
    }

    
    @PostMapping(value="/add", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
    public ResponseEntity<List<Skill>> addSkill(@RequestParam String skillName, HttpSession session) {
    	try {
        // Retrieve the logged-in user from session
    	
    	 System.out.println("Inside SkillController - Received request to add skill: " + skillName);
    	 
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

        // Add skill to the database
        skillService.addSkill(skillName, profile);

        // Fetch updated skill list
        List<Skill> updatedSkills = skillService.getSkillsByProfile(profile);
        
        return ResponseEntity.ok(updatedSkills);
    }
    	catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
		}
    }
    
}
