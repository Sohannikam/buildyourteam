package buildteam.Dao;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import buildteam.MessageDto;
import buildteam.Model.Message;
import buildteam.Model.Request;

@Repository
public class MessageDao {

	 @Autowired
	    private SessionFactory sessionFactory;
	 
	 @Autowired
		private HibernateTemplate hbt;

	 @Transactional
	    public void saveMessage(Message message) {
	        
	    	this.hbt.saveOrUpdate(message);
	    }

	    public List<Message> getMessagesBetweenUsers(int senderId, int receiverId) {
	    	
	    	System.out.println("inside of fetch message of messasge Dao");
	        Session session = sessionFactory.openSession();
	        String hql = "FROM Message WHERE (Msender.id = :senderId AND Mreceiver.id = :receiverId) " +
	                     "OR (Msender.id = :receiverId AND Mreceiver.id = :senderId) ORDER BY timestamp ASC";
	        return session.createQuery(hql, Message.class)
	                .setParameter("senderId", senderId)
	                .setParameter("receiverId", receiverId)
	                .getResultList();
	    }

		public Message getMessageById(int messageId) {
			try {
				
				Session session = sessionFactory.openSession();
				return session.get(Message.class,messageId);
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			

		}

		@Transactional
		public void updateMessage(Message message) {
			
			try {
				Session session = sessionFactory.openSession();
				session.beginTransaction();
				Message message1=session.get(Message.class, message.getId());
				System.out.println("message status is "+message.getStatus());
				if(message!=null)
				{
					System.out.println("message id get succesfuly");
					message1.setStatus(message.getStatus());
					session.update(message1);
					
					session.getTransaction().commit();
			        session.close();
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	        
	        
			
		}
}
