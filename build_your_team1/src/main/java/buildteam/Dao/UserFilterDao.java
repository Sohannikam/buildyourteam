package buildteam.Dao;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import buildteam.Model.Users;


@Repository

public class UserFilterDao {
	
	
 
	 @Autowired
	    private SessionFactory sessionFactory;
	 
	 @Autowired
		private HibernateTemplate hbt;
	 public List<Users> findUsersByFilters(String location, List<String> skills, String experienceRange) {

		    location = (location != null) ? location.trim() : null;
		    boolean hasLocation = location != null && !location.isEmpty();
		    boolean hasSkills = skills != null && skills.stream()
		        .anyMatch(s -> s != null && !s.trim().isEmpty());
		    boolean hasExperience = experienceRange != null && experienceRange.contains("-");

		    System.out.println("Skills received: " + skills);
		    System.out.println("Size of skills: " + (skills != null ? skills.size() : 0));
		    System.out.println("Skill filter applied? " + hasSkills);

		    Session session = hbt.getSessionFactory().openSession();

		    StringBuilder baseQuery = new StringBuilder(
		        "SELECT DISTINCT u FROM Users u " +
		        "JOIN FETCH u.profile p " +
		        "LEFT JOIN FETCH p.skills s "
		    );

		    if (hasLocation) {
		        baseQuery.append("WHERE u.City = :location ");
		    }

		    Query<Users> query = session.createQuery(baseQuery.toString(), Users.class);

		    if (hasLocation) {
		        query.setParameter("location", location);
		    }

		    List<Users> result = query.getResultList();

		    // Initialize collections
		    for (Users u : result) {
		        if (u.getProfile() != null) {
		            Hibernate.initialize(u.getProfile().getProjects());
		            Hibernate.initialize(u.getProfile().getSkills());
		        }
		    }

		    session.close();

		    // 🔥 Skill Filtering in Java
		    if (hasSkills) {
		        result = result.stream()
		            .filter(u -> {
		                if (u.getProfile() == null || u.getProfile().getSkills() == null) return false;

		                List<String> userSkills = u.getProfile().getSkills().stream()
		                    .map(skill -> skill.getSkillName().toLowerCase().trim())
		                    .collect(Collectors.toList());

		                return skills.stream().anyMatch(skill ->
		                    userSkills.contains(skill.toLowerCase().trim()));
		            })
		            .collect(Collectors.toList());
		    }

		    // 🔍 Experience Filtering
		    if (hasExperience) {
		        String[] expValues = experienceRange.split("-");
		        int min = Integer.parseInt(expValues[0].trim());
		        int max = Integer.parseInt(expValues[1].trim());

		        result = result.stream()
		            .filter(u -> {
		                if (u.getProfile() == null || u.getProfile().getProjects() == null) return false;
		                int projectCount = u.getProfile().getProjects().size();
		                return projectCount >= min && projectCount <= max;
		            })
		            .collect(Collectors.toList());
		    }

		    // Debugging Output
		    for (Users u : result) {
		        System.out.println("User ID: " + u.getId());
		        System.out.println("Name: " + u.getName());
		        System.out.println("City: " + u.getCity());
		        System.out.print("Skills: ");
		        if (u.getProfile() != null && u.getProfile().getSkills() != null) {
		            u.getProfile().getSkills().forEach(skill -> System.out.print(skill.getSkillName() + " "));
		        }
		        System.out.println();
		        System.out.println("Project Count: " + 
		            (u.getProfile() != null && u.getProfile().getProjects() != null
		                ? u.getProfile().getProjects().size() : 0));
		        System.out.println("----------------------------------");
		    }

		    return result;
		}


}

