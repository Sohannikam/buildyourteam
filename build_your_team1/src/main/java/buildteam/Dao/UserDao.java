package buildteam.Dao;

import org.hibernate.query.Query;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import buildteam.Model.Message;
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
	        
	        try {
	            // Step 1: Fetch users (excluding the logged-in user)
	            List<Users> users = session.createQuery(
	                "SELECT DISTINCT u FROM Users u " +
	                "JOIN FETCH u.profile p " +
	                "LEFT JOIN FETCH p.skills " +
	                "WHERE u.id != :userId", Users.class)
	                .setParameter("userId", loggedInUserId)
	                .getResultList();

	            // Step 2: Fetch requests where logged-in user is the receiver
	            List<Request> requests = session.createQuery(
	                "FROM Request r WHERE r.receiver.id = :userId", Request.class)
	                .setParameter("userId", loggedInUserId)
	                .getResultList();

	            // Step 3: Extract sender IDs from requests
	            Set<Integer> requestSenders = requests.stream().map(r->r.getSender().getId()).collect(Collectors.toSet());

	            // Step 4: Sort users (priority to users who sent a request)
	            users.sort(Comparator.comparingInt(u -> requestSenders.contains(u.getId()) ? 0 : 1));
	            
	         // DEBUG: Print sorted users before returning
	            System.out.println("Sorted Users:");
	            users.forEach(u -> System.out.println("User: " + u.getId() + " - " + u.getName()));


	            return users;
	        } finally {
	            session.close();
	        }
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

	public Users getUserById(int senderId) {
		// TODO Auto-generated method stub
		
		try {
			
            Session session = hbt.getSessionFactory().openSession();
					
			return session.get(Users.class,senderId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}	

}
