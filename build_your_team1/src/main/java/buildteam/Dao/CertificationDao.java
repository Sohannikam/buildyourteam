package buildteam.Dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import buildteam.Model.Certification;
import buildteam.Model.Profile;
import buildteam.Model.Project;

@Repository
public class CertificationDao {
	
	 @Autowired
	    private SessionFactory sessionFactory;
	    @Autowired
		private HibernateTemplate hbt;
		
	  //create
	  	@Transactional
	    public void save(Certification certification) {
	    	System.out.println("inside Certification dao");
	    	this.hbt.save(certification);
	    }
	  	
	  	public List<Certification> findByProfile(Profile profile) {

	  	    System.out.println("inside of findbyprofilefor Certification");
	  	    Session session = hbt.getSessionFactory().openSession();
	  	    List<Certification> certifications = session.createQuery("FROM Certification WHERE profile = :profile", Certification.class)
	  	                                                .setParameter("profile", profile)
	  	                                                .getResultList();
	  	    
	  	   
	  	    return certifications;
	  		
	  	}

	  	public Optional<Certification> findById(Long cid) {
	  		System.out.println("inside findByID of certification");
	  	    Session session = hbt.getSessionFactory().openSession();
	  	    Certification certification = session.createQuery("FROM Certification WHERE cid = :cid", Certification.class)
	  	                                         .setParameter("cid", cid)
	  	                                         .uniqueResult();
	  	    
	  	    
	  	    return Optional.ofNullable(certification);
	  	    
	  	}


}
