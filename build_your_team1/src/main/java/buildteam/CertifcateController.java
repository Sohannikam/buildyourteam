package buildteam;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import buildteam.Dao.CertificationDao;
import buildteam.Dao.ProjectDao;
import buildteam.Model.Certification;
import buildteam.Model.Profile;
import buildteam.Model.Project;
import buildteam.Model.Users;
import buildteam.Service.ProfileService;

@Controller
public class CertifcateController {
	


    @Autowired
    private ProfileService profileService;
    
    @Autowired
    private UserHandler userhand;
    
    @Autowired
    private ProjectDao projectDao;
    
    @Autowired
    private ServletContext context;
    
    @Autowired
    private CertificationDao certificationDao;

	private static final String UPLOAD_DIR = "C:/Users/sohan/Documents/Uploads/";

    
    @GetMapping(value = "/GetCertification", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Certification>> getCertificate( HttpSession session) {
    	try {
        // Retrieve the logged-in user from session
        	 
    	 
   
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
        List<Certification> updatedCertifications = certificationDao.findByProfile(profile);
        
       
        return ResponseEntity.ok(updatedCertifications);
    }
    	catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
		}
    	
    	
    }

    
    //add Certificate in database
    @PostMapping(value="/addCertification", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Certification>> addCertificate(@RequestParam("CertifcateName") String certificateName,
            @RequestParam("certificationFile") MultipartFile file, HttpSession session) throws IOException {
    	try {
    		
    		


    		System.out.println("Inside Certificate Controller");
        // Retrieve the logged-in user from session
    		
    		   File uploadDir = new File(UPLOAD_DIR); // create a file object representing the upload directory
               if (!uploadDir.exists()) {
                   uploadDir.mkdirs(); // Create the directory if it doesn't exist
               }

               // Define the file path
               String filePath = UPLOAD_DIR + file.getOriginalFilename(); //Creates the full file path by appending the original filename from the uploaded file to the upload directory.
               File convertFile = new File(filePath); //Creates a File object that points to the destination where the file will be saved.
    	 
    	 
    	
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
        
        //Opens a FileOutputStream to write the uploaded file's bytes to the destination path.
        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
        	System.out.println("insdie try of fos");
            fos.write(file.getBytes());
        }

        Certification certification = new Certification();
        certification.setCertifcateName(certificateName);
        certification.setFilePath(filePath);
        certification.setProfile(profile);
        
        certificationDao.save(certification);
     // Fetch updated Certification list
        List<Certification> updatedCertifications = certificationDao.findByProfile(profile);

          
//        System.out.println(updatedProectList);
        return ResponseEntity.ok(updatedCertifications);
    }
    	catch (Exception e) {
    		System.out.println("hello there is error in catch");
    		e.printStackTrace();
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
		}
    }
    
    @GetMapping("/downloadCertification")
    public void viewCertification(@RequestParam("cid") Long cid, HttpServletResponse response,HttpSession session) throws IOException {
        
    	System.out.println("inside viewCertification");
    	
    	 Users user = (Users) session.getAttribute("user");
         System.out.println(user);
         if (user == null) {
         	System.out.println("inside user==null");
         }

         // Get Profile based on logged-in user
         Profile profile = profileService.getProfileByUser(user);
         if (profile == null) {
         	 System.out.println("inside profile==null");
            
         }
        // Fetch the certificate by ID and ensure it belongs to the logged-in user
        Optional<Certification> certOptional = certificationDao.findById(cid);

       // Checks if the certificate belongs to the logged-in user by comparing the profile IDs.
        if (certOptional.isPresent()) {
        	System.out.println("inside cert is present");
            Certification cert = certOptional.get();

            // Ensure the certificate belongs to the logged-in user
            if (!cert.getProfile().getPid().equals(profile.getPid())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized to view this file.");
                return;
            }

            File file = new File(cert.getFilePath());

            if (file.exists()) {
            	
            	System.out.println("file is exists");
                // Set MIME type based on file type
                if (file.getName().toLowerCase().endsWith(".pdf")) {
                    response.setContentType("application/pdf");
                } else {
                    response.setContentType(Files.probeContentType(file.toPath()));
                }

                // Open file in browser
                
//                response.setHeader("Content-Disposition", "inline; filename=" + file.getName());
//                	This sets an HTTP header called Content-Disposition to tell the browser how to handle the file.
//                	"inline": This value instructs the browser to try to display the file in the browser window (if it's viewable in the browser, like PDFs, images, etc.). If the browser cannot display it, the user will be prompted to download it.
//                	filename=" + file.getName(): This provides the name of the file as the filename parameter, which helps the browser know what name to use when the user chooses to save the file. For example, if the file is named certificate.pdf, the browser will suggest the name certificate.pdf for saving the file.
                
                response.setHeader("Content-Disposition", "inline; filename=" + file.getName());
                Files.copy(file.toPath(), response.getOutputStream());
                response.getOutputStream().flush();
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Certificate not found.");
        }
    }


}
