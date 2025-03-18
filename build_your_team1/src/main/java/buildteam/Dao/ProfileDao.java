package buildteam.Dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;

import buildteam.Model.Profile;
import buildteam.Model.Users;

@Repository
public class ProfileDao {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
	private HibernateTemplate hbt;
   
	
	//create
	@Transactional
	public void save(Profile prof)
	{
		System.out.println("inside save profile");
		this.hbt.saveOrUpdate(prof);
	}
	
	public Profile getProfile(Long PID)
	{
		System.out.println("inside fin profile by profile id");
		Session session=hbt.getSessionFactory().getCurrentSession();
		System.out.println("profile id is"+PID);
		return session.createQuery("FROM Profile WHERE Pid= :profid",Profile.class)
				.setParameter("profid",PID)	
				.uniqueResult();
	}

    public Profile findByUser(Users user) {
    	
    	System.out.println("inside of findByUser");
    	Session session = hbt.getSessionFactory().openSession();
    	System.out.println("user is"+user.getId());
        return session.createQuery("FROM Profile WHERE user_id = :user", Profile.class)
                      .setParameter("user", user)
                      .uniqueResult();
       
    }
}
