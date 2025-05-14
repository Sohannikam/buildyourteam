package buildteam.Dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import buildteam.Model.Blog;
import buildteam.Model.Users;

@Repository
public class BlogDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private HibernateTemplate hbt;

    @Transactional
    public void save(Blog blog) {
    	
    	
        System.out.println("Inside BlogDao save");
        hbt.saveOrUpdate(blog);
    }

    public List<Blog> findBlogsByUser() {
    	
    	System.out.println("inside of BlogDao findblogsbyuser");
        Session session = hbt.getSessionFactory().openSession();
        return session.createQuery("SELECT b FROM Blog b JOIN FETCH b.user ORDER BY b.createdDate DESC", Blog.class)
                .getResultList();
    }
}
