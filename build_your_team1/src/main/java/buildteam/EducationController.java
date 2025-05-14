package buildteam;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import buildteam.Dao.EducationDAO;
import buildteam.Dao.UserDao;
import buildteam.Model.Education;
import buildteam.Model.EducationLevel;
import buildteam.Model.Profile;
import buildteam.Model.Users;
import buildteam.Service.ProfileService;

@RestController

public class EducationController {
    
    @Autowired
    private EducationDAO educationDAO;
    
    @Autowired
    private ProfileService profileService;
    
    @Autowired
    private UserDao userDao;

    @PostMapping(value = "/addEducation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Education>> addEducation(
            @RequestParam EducationLevel educationLevel,
            @RequestParam String status,
            @RequestParam double percentage,
            @RequestParam int passingYear,
            @RequestParam String Branch,
            HttpSession session) 
    {
    	
    	System.out.println("Inside of education controller");
        try {
            System.out.println("Inside EducationController - Received request to add education: " + educationLevel);

            // Retrieve logged-in user from session
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

            // Save education data
            Education education = new Education();
            education.setEducationLevel(educationLevel);
            education.setStatus(status);
            education.setPercentage(percentage);
            education.setPassingYear(passingYear);
            education.setProfile(profile);
            education.setBranch(Branch);
            
            educationDAO.saveEducation(education);


            // Fetch updated education list
            List<Education> updatedEducationList = educationDAO.findByProfile(profile);

            return ResponseEntity.ok(updatedEducationList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }
    
    // GET method to fetch education details
    @GetMapping(value = "/GetEducation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Education>> getEducation(HttpSession session) {
        try {
            System.out.println("Inside GetEducation Controller");

            // Retrieve the logged-in user from the session
            Users user = (Users) session.getAttribute("user");

            if (user == null) {
                System.out.println("User not logged in.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Get the profile of the logged-in user
            Profile profile = profileService.getProfileByUser(user);
            if (profile == null) {
                System.out.println("Profile not found.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            // Fetch the education details from the database
            List<Education> educationList = educationDAO.findByProfile(profile);

            return ResponseEntity.ok(educationList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET method to fetch education details
    @GetMapping(value = "/GetEducationforotheruser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Education>> getEducationforotheruser(@RequestParam("userId") int userId,HttpSession session) {
        try {
            System.out.println("Inside GetEducation Controller");

            // Retrieve the logged-in user from the session
            Users user = userDao.findUserById(userId); // You must have userService injected

            if (user == null) {
                System.out.println("User not logged in.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Get the profile of the logged-in user
            Profile profile = profileService.getProfileByUser(user);
            if (profile == null) {
                System.out.println("Profile not found.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            // Fetch the education details from the database
            List<Education> educationList = educationDAO.findByProfile(profile);

            return ResponseEntity.ok(educationList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

 