package buildteam.Dao;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import buildteam.Model.Profile;
import buildteam.Model.Users;

@Repository
public class ProfileImageDao {
	
	 @Autowired
	    private SessionFactory sessionFactory;
	    
	    @Autowired
		private HibernateTemplate hbt;
	   
		
		//create
		@Transactional
		public void UpdateProfile(Profile profile,int User_ids)
		{
			System.out.println("inside updateProfile");
			
		    
		    Session session = hbt.getSessionFactory().openSession();
		    session.beginTransaction();
		    
		    Query query = session.createQuery("UPDATE Profile p SET p.profilePicture = :imagePath WHERE p.user.id = :userId");
		    query.setParameter("imagePath", profile.getProfilePicture());
		    query.setParameter("userId", User_ids);
		    
		    int result = query.executeUpdate();
		    session.getTransaction().commit();
		    session.close();
		    
		    System.out.println(result + " record(s) updated.");
		    
		    if (result == 0) {
		        System.out.println("No record updated, check userId.");
		    }
            
          
		}
		

}