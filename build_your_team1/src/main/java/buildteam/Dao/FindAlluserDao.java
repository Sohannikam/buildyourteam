package buildteam.Dao;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;

import buildteam.Model.Certification;
import buildteam.Model.Profile;
import buildteam.Model.Users;

public class FindAlluserDao {
	 @Autowired
	    private SessionFactory sessionFactory;
	    
	    @Autowired
		private HibernateTemplate hbt;
	   
	    public List<Users> getUsers() {
	System.out.println("inside of findByUser");
	Session session = hbt.getSessionFactory().openSession();
	List<Users> user = session.createQuery("FROM Users", Users.class)
                  .getResultList();
	session.close();
	return user;
	
	    }

}

