package buildteam;

import java.io.*;
import java.util.Base64;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import buildteam.Dao.ProfileDao;
import buildteam.Dao.ProfileImageDao;
import buildteam.Model.Profile;
import buildteam.Model.Users;
import buildteam.Service.ProfileService;

@Controller
public class ImageController {

	@Autowired
    private ProfileService profileService;
	
	@Autowired
	private ProfileImageDao profileImageDao;
    @Autowired
    private ProfileDao profileDAO;
    

    @PostMapping(value="/uploadImage", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> uploadProfilePicture(@RequestParam("profilePicture") MultipartFile file, HttpSession session) {
        try {
            // Convert the uploaded file to byte array
            byte[] profilePicBytes = file.getBytes();
            
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
            
            int userId=user.getId();
            
            profile.setProfilePicture(profilePicBytes);
            
            profileImageDao.UpdateProfile(profile,userId );
            
            String base64Image = Base64.getEncoder().encodeToString(profile.getProfilePicture());

            
            System.out.println("profile pic is"+ profile.getProfilePicture());
            
            return ResponseEntity.ok("{\"image\":\"" + base64Image + "\"}");  
            } 
        catch (IOException e) {
        	System.out.println("inside catch bloclk");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image.");
        }
}
    
    @GetMapping(value = "/displayImage", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> displayProfilePicture(HttpSession session) {
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Profile profile = profileDAO.findByUser(user);

        if (profile != null && profile.getProfilePicture() != null) {
            String base64Image = Base64.getEncoder().encodeToString(profile.getProfilePicture());
            return ResponseEntity.ok("{\"image\":\"" + base64Image + "\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"image\": null}");
        }
    }


}