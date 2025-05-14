package buildteam;

import java.io.Console;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import buildteam.Dao.BlogDAO;
import buildteam.Model.Blog;
import buildteam.Model.Profile;
import buildteam.Model.Users;
import buildteam.Service.ProfileService;

import org.springframework.web.bind.annotation.*;

@Controller
public class BlogController {
	
	@Autowired
	private BlogDAO blogDAO;
	
	@Autowired
	private ProfileService profileService;
	
	@PostMapping(value = "/addBlogss", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity <Map<String, String>>  addBlog(@RequestParam String title,
	                                          @RequestParam String description,
	                                          @RequestParam(value = "image", required = false) MultipartFile image,
	                                          HttpSession session) {
		System.out.println("inside of addBlog controller");
	    try {
	        System.out.println("Inside addBlog Controller");
	        Users user = (Users) session.getAttribute("user");

	        if (user == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	        }

	        Blog blog = new Blog();
	        blog.setTitle(title);
	        blog.setDescription(description);
	        blog.setUser(user);
	        blog.setCreatedDate(new Date());

	        if (image != null && !image.isEmpty()) {
	            blog.setImage(image.getBytes());
	        }


	        blogDAO.save(blog);
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "Blog added successfully!");
	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
	@GetMapping(value = "/getAllBlogs", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<BlogDTO>> getAllBlogs(HttpSession session) {
	    try {
	    	
	    	System.out.println("inside of getAll Blogs");
	    	
	    	
		        
	        List<Blog> blogs = blogDAO.findBlogsByUser();
	        
	        List<BlogDTO> blogDTOs = blogs.stream().map(blog -> {
	            BlogDTO dto = new BlogDTO();
	            dto.setBid(blog.getBid());
	            dto.setTitle(blog.getTitle());
	            dto.setDescription(blog.getDescription());
	            dto.setCreatedDate(blog.getCreatedDate().getTime()); // Date to long

	            if (blog.getImage() != null) {
	                dto.setImage(Base64.getEncoder().encodeToString(blog.getImage()));
	            }

	            
	             Users user = blog.getUser();
	            if (user != null) {
	                dto.setUser(new UserDTO(user.getId(), user.getName()));
	            }
	            
	            

	            return dto;
	        }).collect(Collectors.toList());
	        
	        blogDTOs.forEach(dto -> {
	            System.out.println("Blog ID: " + dto.getBid());
	            System.out.println("Title: " + dto.getTitle());

	         

	            if (dto.getUser() != null) {
	                System.out.println("User ID: " + dto.getUser().getId());
	                System.out.println("User Name: " + dto.getUser().getName());
	            }

	            System.out.println("===========");
	        });



	        return ResponseEntity.ok(blogDTOs);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

	
	
}
