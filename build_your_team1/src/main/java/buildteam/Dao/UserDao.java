package buildteam.Dao;

import org.hibernate.query.Query;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import buildteam.Model.Request;
import buildteam.Model.Users;

@Repository
public class UserDao {
	
	@Autowired
	private HibernateTemplate hbt;
	
	 @Autowired
	    private SessionFactory sessionFactory;
	
	//create
	@Transactional
	public void createUser(Users product)
	{
		this.hbt.saveOrUpdate(product);
	} 
	
	//login user
	public Users getUsers(String email, String pass) {
        try {
            Session session = hbt.getSessionFactory().openSession();
            Query<Users> query = session.createQuery("from Users where email=:em and pass=:pwd", Users.class);
            query.setParameter("em", email);
            query.setParameter("pwd", pass);
            
            System.out.println("inside get user method of userDao");
            System.out.println(email);
            Users user = query.uniqueResult();
            session.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public boolean doesEmailExist(String email) {
	    try {
	        Session session = hbt.getSessionFactory().openSession();
	        Query<Long> query = session.createQuery("select count(*) from Users where email=:em", Long.class);
	        query.setParameter("em", email);
	        
	        Long count = query.uniqueResult();
	        session.close();
	        return count > 0; // If count is greater than 0, email exists
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false; // If there's an error, return false
	    }
	}

	public boolean doesPhoneExist(long phone) {
	    try {
	        Session session = hbt.getSessionFactory().openSession();
	        Query<Long> query = session.createQuery("select count(*) from Users where phone=:ph", Long.class);
	        query.setParameter("ph", phone);
	        
	        Long count = query.uniqueResult();
	        session.close();
	        return count > 0; // If count is greater than 0, phone exists
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false; // If there's an error, return false
	    }
	}

	public boolean doesPassExist(String pass) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

	public List<Users> getAllUsersWithProfilesAndSkills(int loggedInUserId) {
		// TODO Auto-generated method stub
		
		System.out.println("inside getalluserswithskllls Dao");

	        Session session = hbt.getSessionFactory().openSession();
	        
	        // First query to get users excluding the logged-in user
	        List<Users> users = session.createQuery(
	            "SELECT u FROM Users u " +
	            "JOIN FETCH u.profile p " +
	            "WHERE u.id != :userId " +
	            "ORDER BY u.id", 
	            Users.class)
	            .setParameter("userId", loggedInUserId)
	            .getResultList();

	        // Close session after fetching users
	        session.close();

	        // Load sent requests and skills after fetching users
	        loadSentRequestsAndSkills(users, loggedInUserId);

	     
	        			
	    	return users;
	        
	    
	        
	         // If count is greater than 0, phone exists
	   
	}
	
	
	private void loadSentRequestsAndSkills(List<Users> users, int loggedInUserId) {
	    // Open session again to fetch sent requests and skills
	    Session session = sessionFactory.openSession();

	    for (Users user : users) {
	        // Fetch sent requests for each user
	        List<Request> sentRequests = session.createQuery(
	            "SELECT r FROM Request r WHERE r.sender.id = :senderId", 
	            Request.class)
	            .setParameter("senderId", user.getId())
	            .getResultList();

	        // Set the sent requests for the user
	        user.setSentRequests(sentRequests);

	        // Fetch and initialize skills for the profile of each user
	        if (user.getProfile() != null) {
	            Hibernate.initialize(user.getProfile().getSkills());
	        }
	    }

	    // Close session after loading sent requests and skills
	    session.close();
	}

	
//    List<Users> user = session.createQuery("SELECT DISTINCT u FROM Users u JOIN FETCH u.profile p LEFT JOIN FETCH p.skills", Users.class)

	public Users findUserById(int userId) {
		// TODO Auto-generated method stub
		try {
			
	    	System.out.println("inside find user by his id");

	    	
            Session session = hbt.getSessionFactory().openSession();
            Query<Users> query = session.createQuery("from Users where id=:user_id", Users.class);
            query.setParameter("user_id", userId);
            
            System.out.println("inside get user method of userDao");
   
            Users user = query.uniqueResult();
            session.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}	

}
