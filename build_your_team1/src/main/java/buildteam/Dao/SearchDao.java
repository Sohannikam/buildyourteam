package buildteam.Dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import buildteam.UserDTO;
import buildteam.Model.Certification;
import buildteam.Model.Skill;
import buildteam.Model.Users;

@Repository
public class SearchDao {
	

	
	   @Autowired
		private HibernateTemplate hbt;

	public List<Users> findUsersBySearch(String username) {
		String namePattern=username + "%";
		namePattern.trim();
		System.out.println("username is "+namePattern);
		System.out.println("inside of search dao");
		System.out.println("inside of findbyprofile");
    	Session session = hbt.getSessionFactory().openSession();
        List<Users> useduser=session.createQuery("FROM Users WHERE name LIKE :nameQuery", Users.class)
                      .setParameter("nameQuery", namePattern)
                      .getResultList();
        
     // Force initialize the required collections
        for (Users user : useduser) {
            if (user.getProfile() != null) {
                Hibernate.initialize(user.getProfile().getSkills());
            }
        }
        session.close();
        return useduser;
        
    
	}

}
