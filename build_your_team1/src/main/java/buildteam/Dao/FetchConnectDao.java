package buildteam.Dao;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import buildteam.Model.Request;
import buildteam.Model.RequestStatus;
import buildteam.Model.Users;

@Repository
public class FetchConnectDao {
	

	@Autowired
	private HibernateTemplate hbt;
	
	 @Autowired
	    private SessionFactory sessionFactory;
	


	 public List<Users> getAcceptedUsersWithProfilesAndSkills(int loggedInUserId) {
		    System.out.println("Fetching users with accepted connections");

		    Session session = hbt.getSessionFactory().openSession();
		    try {
		        // Step 1: Get accepted requests involving logged-in user
		        List<Request> acceptedRequests = session.createQuery(
		            "FROM Request r WHERE (r.sender.id = :userId OR r.receiver.id = :userId) AND r.status = :status",
		            Request.class)
		            .setParameter("userId", loggedInUserId)
		            .setParameter("status", RequestStatus.Accepted)
		            .getResultList();

		        // Step 2: Extract connected user IDs (excluding self)
		        Set<Integer> connectedUserIds = acceptedRequests.stream()
		            .map(r -> (r.getSender().getId() == loggedInUserId) ? r.getReceiver().getId() : r.getSender().getId())
		            .collect(Collectors.toSet());

		        if (connectedUserIds.isEmpty()) return Collections.emptyList();

		        // Step 3: Fetch full user data including profiles and skills
		        List<Users> users = session.createQuery(
		            "SELECT DISTINCT u FROM Users u " +
		            "JOIN FETCH u.profile p " +
		            "LEFT JOIN FETCH p.skills " +
		            "WHERE u.id IN (:ids)", Users.class)
		            .setParameter("ids", connectedUserIds)
		            .getResultList();

		        return users;

		    } finally {
		        session.close();
		    }
		}

	 public List<Users> getPendingUsers(int loggedInUserId) {
		    System.out.println("Fetching users with pending connections");

		    Session session = hbt.getSessionFactory().openSession();
		    try {
		        // Get only pending requests sent by the logged-in user
		        List<Request> pendingRequests = session.createQuery(
		            "FROM Request r WHERE r.sender.id = :userId AND r.status = :status", Request.class)
		            .setParameter("userId", loggedInUserId)
		            .setParameter("status", RequestStatus.Pending)
		            .getResultList();

		        // Get the receiver IDs from those requests
		        Set<Integer> receiverUserIds = pendingRequests.stream()
		            .map(r -> r.getReceiver().getId())
		            .collect(Collectors.toSet());

		        if (receiverUserIds.isEmpty()) return Collections.emptyList();

		        // Fetch full user data including profile and skills
		        List<Users> users = session.createQuery(
		            "SELECT DISTINCT u FROM Users u " +
		            "JOIN FETCH u.profile p " +
		            "LEFT JOIN FETCH p.skills " +
		            "WHERE u.id IN (:ids)", Users.class)
		            .setParameter("ids", receiverUserIds)
		            .getResultList();

		        return users;
		    } finally {
		        session.close();
		    }
		}


}
