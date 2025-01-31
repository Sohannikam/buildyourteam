package buildteam.Dao;

import org.hibernate.query.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import buildteam.Model.Users;

@Repository
public class UserDao {
	
	@Autowired
	private HibernateTemplate hbt;
	
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
	        return count > 0; // If count is greater than 0, email exists
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false; // If there's an error, return false
	    }
	}	

}
