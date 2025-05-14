package buildteam.Dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;


import buildteam.Model.Users;

@Repository

public class UpdateProfileDao {
	
	 @Autowired
	    private HibernateTemplate hbt;

	    @Autowired
	    private SessionFactory sessionFactory;


	public void updateUser(Users user) {
		System.out.println("inside update profile dao");
		// TODO Auto-generated method stub
		
		 Session session = sessionFactory.openSession();
	        session.beginTransaction();
	            session.update(user);
	        
	        session.getTransaction().commit();
	        session.close();
	}



	    
}
