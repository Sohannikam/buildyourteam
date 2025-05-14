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


.modal40 {
  position: fixed;
  z-index: 9999;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0,0,0,0.5);
}
.modal40-content {
  background-color: #fff;
  margin: 5% auto;
  padding: 20px;
  width: 56%;
  border-radius: 10px;
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
         <button class="search-btn" onclick="SearchUsersByName()">Search</button>
         
        <button class="filter-btn" onclick="openFilterModal()">Filter</button>
        
        </div>
        
          <!-- Filter Modal -->
    
  <div id="filterModal" class="modal1">
    <div class="modal-header1">
        <h2>Filter Users</h2>
        <button class="close-btn1"  onclick="closeFilterModal()">✖</button>
    </div>

    <div class="modal-content1">
        <!-- Location Filter -->
        <label for="location" >Location:</label>
        <select id="location" >
            <option value="">Select Location</option>
           
        </select>

        <!-- Skills Filter -->
        <label for="skills">Skills:</label>
        <input  id="skills" type="text" placeholder="Enter the skills here">

        <!-- Availability Filter -->
      <!--   <label>Availability:</label>
        <div class="radio-group">
            <input type="radio" name="availability" value="full-time"> Full Time
            <input type="radio" name="availability" value="part-time"> Part Time
            <input type="radio" name="availability" value="not-available"> Not Available
        </div> -->

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
        
        <!-- Messaging Modal -->
<!-- WhatsApp-Style Chat Modal -->

<div id="messageModal" class="modal20" data-receiver-id="">
    <div class="modal20-header">
        <img id="messageUserImg" src="" alt="User Image" class="modal-profile-pic">
        <h3 id="messageUserName"></h3>
        <span class="close" onclick="$('#messageModal').hide()">&times;</span>
    </div>

    <div class="modal20-body">
        <div id="chatMessages">
            <!-- Chat messages will be dynamically added here -->
        </div>
    </div>

    <div class="modal20-footer">
    
     <label for="fileInput" class="file-upload-label">
           📁
        </label>
        <input type="file" id="fileInput" style="display: none;" onchange="previewFile()">

        <!-- File Preview -->
        <span id="filePreview" class="file-preview"></span>
        
        <textarea id="messageInput" placeholder="Type a message..."></textarea>
        <button class="send-btn20" id="sendBtn0" >Send</button>
    </div>
</div>
        
        <!-- Modal for User Profile -->
<div id="userProfileModal" class="modal40" style="display:none;">
  <div class="modal40-content">
    <span class="close" onclick="closeUserProfileModal()">&times;</span>
    <div id="userProfileContent">
      <!-- User profile (profileforotheruser.jsp content) will load here dynamically -->
    </div>
  </div>
</div>
        
        
         <!-- Card Container -->
    <div class="main-content">
    	

        <!-- Sample Card for Demonstration -->
    
        
        <!-- Repeat for other users (Using JSTL) -->
       <!-- Add this div where the user cards will be inserted -->
       
			<div id="user-container"></div>
 
    </div>
        
</div>

</body>
</html>
