package buildteam.Dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import buildteam.Model.Education;
import buildteam.Model.Profile;
import buildteam.Model.Project;



	
@Repository
public class EducationDAO {
    
	@Autowired
	private HibernateTemplate hbt;
	  
		
	@Autowired
	private SessionFactory sessionFactory;
	

    @Transactional
    public void saveEducation(Education education) {
        try (Session session = sessionFactory.openSession()) {
            session.save(education);
           
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }
    
	public List<Education> findByProfile(Profile profile) {
		
		System.out.println("inside of findby profile for education dao");
    	
    	System.out.println("inside of findbyprofileforproject");
    	Session session = hbt.getSessionFactory().openSession();
        return session.createQuery("FROM Education WHERE profile = :profile", Education.class)
                      .setParameter("profile", profile)
                      .getResultList();
    }
}
