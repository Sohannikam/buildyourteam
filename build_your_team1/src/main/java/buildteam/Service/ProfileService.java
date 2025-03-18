package buildteam.Service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import buildteam.Model.Profile;
import buildteam.Model.Users;
import buildteam.Dao.*;

@Service
public class ProfileService {

    @Autowired
    private ProfileDao profileDAO;
    
    

    public Profile getProfileByUser(Users user) {
    	
    	System.out.println("inside profileserver");
        return profileDAO.findByUser(user);
    }
}

