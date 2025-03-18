package buildteam.Dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import buildteam.Model.Profile;
import buildteam.Model.Project;
import buildteam.Model.Skill;

@Repository
public class ProjectDao {

	  @Autowired
	    private SessionFactory sessionFactory;
	    @Autowired
		private HibernateTemplate hbt;
		
	  //create
	  	@Transactional
	    public void save(Project project) {
	    	System.out.println("inside Project dao");
	    	this.hbt.saveOrUpdate(project);
	    }
	  	
	  	public List<Project> findByProfile(Profile profile) {
	    	
	    	System.out.println("inside of findbyprofileforproject");
	    	Session session = hbt.getSessionFactory().openSession();
	        return session.createQuery("FROM Project WHERE profile = :profile", Project.class)
	                      .setParameter("profile", profile)
	                      .getResultList();
	    }
}
