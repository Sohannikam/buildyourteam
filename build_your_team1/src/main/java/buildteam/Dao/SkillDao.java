package buildteam.Dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;

import buildteam.Model.Skill;
import buildteam.Model.Profile;
import java.util.List;

@Repository
public class SkillDao {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
	private HibernateTemplate hbt;
	
  //create
  	@Transactional
    public void save(Skill skill) {
    	System.out.println("inside skill dao");
    	this.hbt.save(skill);
    }

    public List<Skill> findByProfile(Profile profile) {
    	
    	System.out.println("inside of findbyprofile");
    	Session session = hbt.getSessionFactory().openSession();
        return session.createQuery("FROM Skill WHERE profile = :profile", Skill.class)
                      .setParameter("profile", profile)
                      .getResultList();
    }
}
