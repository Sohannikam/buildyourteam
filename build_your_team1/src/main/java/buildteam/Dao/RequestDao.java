package buildteam.Dao;

import buildteam.Model.Request;

import buildteam.Model.Users;
import buildteam.Model.RequestStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RequestDao {

    @Autowired
    private HibernateTemplate hbt;

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void sendRequest(Users sender, Users receiver) {
    	
    	System.out.println("inside of sendRequest of RequestDao");
        Request request = new Request(sender, receiver, RequestStatus.Pending);
        hbt.saveOrUpdate(request);
    }

    public List<Request> getPendingRequestsForUser(int userId) {
    	
    	System.out.println("inside of getPendingRequests");
        Session session = sessionFactory.openSession();
        Query<Request> query = session.createQuery( "FROM Request r " +
                "JOIN FETCH r.sender s " +
                "JOIN FETCH r.receiver rc " +
                "WHERE rc.id = :userId AND r.status = 'Pending'",
                Request.class);
        query.setParameter("userId", userId);
        List<Request> requests = query.getResultList();
        System.out.println("request for login users are"+requests);
        session.close();
        return requests;
    }

    @Transactional
    public void updateRequestStatus(int requestId, RequestStatus status) {
    	
    	System.out.println("inside of updateRequestStat");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Request request = session.get(Request.class, requestId);
        if (request != null) {
        	System.out.println("reqeust accesed sucssesfully");
            request.setStatus(status);
            session.update(request);
        }
        session.getTransaction().commit();
        session.close();
    }

	public List<Request> getStausofRequest(int SenderId,int RecivedId) {
		// TODO Auto-generated method stub
		
		System.out.println("inside of getPendingRequests");
        Session session = sessionFactory.openSession();
        Query<Request> query = session.createQuery("FROM Request WHERE sender.id = :userId AND receiver.id= :Recieve_Id", Request.class);
        query.setParameter("userId", SenderId);
        query.setParameter("Recieve_Id", RecivedId);
      
        List<Request> requests = query.getResultList();
        System.out.println("request for login users are"+requests);
        session.close();
        return requests;
		
	}

	public List<Request> getAllRequestForuser() {
		// TODO Auto-generated method stub
		
		System.out.println("inside of getPendingRequests");
        Session session = sessionFactory.openSession();
        Query<Request> query = session.createQuery( "FROM Request r " +
                "JOIN FETCH r.sender s " +
                "JOIN FETCH r.receiver rc ",
                Request.class);

        List<Request> requests = query.getResultList();
        System.out.println("request for login users are"+requests);
        session.close();
        return requests;
		
	}

	public void updateRequestStatusForReject(int requestId) {
		
		System.out.println("inside of updateRequestStat");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Request request = session.get(Request.class, requestId);
        if (request != null) {
        	System.out.println("reqeust accesed sucssesfully");
         
            session.delete(request);
        }
        session.getTransaction().commit();
        session.close();
	
	}
	
	public int getReceiverIdFromRequestId(int requestId) {
	    Session session = sessionFactory.openSession();
	    Request request = session.get(Request.class, requestId);
	    int receiverId = request.getSender().getId(); // Assuming you have a reference to receiver in Request entity
	    session.close();
	    return receiverId;
	}
	
	public int getIdifReciveridisLoginUser(int requestId) {
		System.out.println("insde of getIdifReciveridisLoginUser ");
	    Session session = sessionFactory.openSession();
	    Request request = session.get(Request.class, requestId);
	    int receiverId = request.getReceiver().getId(); // Assuming you have a reference to receiver in Request entity
	    session.close();
	    return receiverId;
	}

}
