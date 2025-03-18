<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<%@page isELIgnored="false"%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Find Your Partner</title>
    <link href="<c:url value='/Resource/css/style.css' />" rel="stylesheet">
    <link href="<c:url value='/Resource/css/style1.css' />" rel="stylesheet">
    <link href="<c:url value='/Resource/css/style3.css' />" rel="stylesheet">
    
    <style>
    
    .accept-btn {
    background-color: palegreen;
    color: black;
    border: none;
    padding: 5px 15px;
    margin-right: 10px;
    cursor: pointer;
    border-radius: 5px;
}

.reject-btn {
    background-color: red;
    color: white;
    border: none;
    padding: 5px 15px;
    cursor: pointer;
    border-radius: 5px;
}

.button-container {
    display: flex;
    gap: 10px; /* Space between buttons */
    color: black;
  border: none;
  
  font-size: 1rem;
  position: absolute;
  top: 10px;
  right: 10px;
  z-index: 0;
  width: auto;
}


.accept-btn:hover {
    background-color: #8fdb8f;
}

.reject-btn:hover {
    background-color: rgb(240, 60, 60);
}
   	 
    </style>
</head>
<body>
<div class="page-container">


    
    
     <!-- Back to Dashboard Button -->
    <button id="back-to-dashboard-1" class="back-btn1">
    Back to Dashboard
</button>

    
    <div class="serach-container">
  
        <input type="text" id="searchBox" class="search-box" placeholder="Search for users...">
         <button class="search-btn">Search</button>
         
        <button class="filter-btn" onclick="openFilterModal()">Filter</button>
        
        </div>
        
          <!-- Filter Modal -->
    
  <div id="filterModal" class="modal1">
    <div class="modal-header1">
        <h2>Filter Users</h2>
        <button class="close-btn1" onclick="closeFilterModal()">✖</button>
    </div>

    <div class="modal-content1">
        <!-- Location Filter -->
        <label for="location">Location:</label>
        <select id="location">
            <option value="">Select Location</option>
            <option value="Delhi">Delhi</option>
            <option value="Mumbai">Mumbai</option>
            <option value="Bangalore">Bangalore</option>
        </select>

        <!-- Skills Filter -->
        <label for="skills">Skills:</label>
        <select id="skills" multiple>
            <c:forEach var="skill" items="${uniqueSkills}">
                <option value="${skill}">${skill}</option>
            </c:forEach>
        </select>

        <!-- Availability Filter -->
        <label>Availability:</label>
        <div class="radio-group">
            <input type="radio" name="availability" value="full-time"> Full Time
            <input type="radio" name="availability" value="part-time"> Part Time
            <input type="radio" name="availability" value="not-available"> Not Available
        </div>

        <!-- Experience Filter -->
        <label for="experience">Experience (Projects Done):</label>
        <select id="experience">
            <option value="">Select Experience</option>
            <option value="0-2">0-2 Projects</option>
            <option value="3-5">3-5 Projects</option>
            <option value="6+">6+ Projects</option>
        </select>

        <!-- Apply Button -->
        <button class="save-btn1" onclick="applyFilters()">Apply Filters</button>
    </div>
</div>
        
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
        
        <!-- Repeat for other users (Using JSTL) -->
       <!-- Add this div where the user cards will be inserted -->
       
			<div id="user-container"></div>
 
    </div>
        
</div>

</body>
</html>
