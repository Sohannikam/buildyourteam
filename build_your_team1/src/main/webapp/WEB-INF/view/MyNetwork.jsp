<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Network</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="<c:url value='/Resource/css/style.css' />" rel="stylesheet">
    <link href="<c:url value='/Resource/css/Style4.css' />" rel="stylesheet">
    <link href="<c:url value='/Resource/css/style3.css' />" rel="stylesheet">
    
</head>
<body>


<!-- Back to Dashboard Button -->
	<button id="back-to-dashboard-2" class="back-btn3" >Back to
		Dashboard</button>


    <!-- My Network Section -->
    <div class="container1 mt-4">

      <!-- Small Navigation Tabs -->
        <ul class="networknav nav nav-tabs justify-content-center w-100" id="network-tabs">
            <li class="nav-item flex-fill">
                <a class="nav-link active text-center" id="followers1" href="#" data-section="followers">Followers</a>
            </li>
            <li class="nav-item flex-fill">
                <a class="nav-link text-center" id="following1" href="#" data-section="following">Following</a>
            </li>
            <li class="nav-item flex-fill">
                <a class="nav-link text-center" id="pending1" href="#" data-section="pending">Pending Requests</a>
            </li>
        </ul>

        <!-- Content Section for Followers & Following -->
        <div id="network-content" class="allusers mt-4">
                <!-- Card Container -->
    <div class="main-content">
    	

        <!-- Sample Card for Demonstration -->
        <div class="user-card">
            <img src="<c:url value='' />" alt="Profile Picture" class="profile-pic">
            
            <div class="user-info">
                <h3>John Doe</h3>
                <p>Skills: Java, Spring, Hibernate</p>
                <p class="user-about">About: Passionate developer looking for a team.</p>
                <button class="send-request-btn" onclick="sendRequest('John Doe')">Send Request</button>
           </div>
        </div>
        
         <div id="followers" class="tab-content">
         <h1>this is followers tab</h1>
                <c:forEach var="follower" items="${followersList}">
                    <div class="user-card">
                        <img src="<c:url value='/Resource/images/${follower.profilePic}' />" alt="Profile Picture" class="profile-pic">
                        <div class="user-info">
                            <h3>${follower.name}</h3>
                            <p>Skills: ${follower.skills}</p>
                            <p class="user-about">About: ${follower.about}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div id="following" class="tab-content" style="display: none;">
                     <h1>this is following tab</h1>
            
                <c:forEach var="following" items="${followingList}">
                    <div class="user-card">
                        <img src="<c:url value='/Resource/images/${following.profilePic}' />" alt="Profile Picture" class="profile-pic">
                        <div class="user-info">
                            <h3>${following.name}</h3>
                            <p>Skills: ${following.skills}</p>
                            <p class="user-about">About: ${following.about}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
         
            <div id="pending" class="tab-content" style="display: none;">
                     <h1>this is Pending tab</h1>
            
                <c:forEach var="request" items="${pendingRequestsList}">
                    <div class="user-card">
                        <img src="<c:url value='/Resource/images/${request.profilePic}' />" alt="Profile Picture" class="profile-pic">
                        <div class="user-info">
                            <h3>${request.name}</h3>
                            <p>Skills: ${request.skills}</p>
                            <p class="user-about">About: ${request.about}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        
    </div>
        </div>

    </div>
    
  
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
 
    

  
</body>
</html>