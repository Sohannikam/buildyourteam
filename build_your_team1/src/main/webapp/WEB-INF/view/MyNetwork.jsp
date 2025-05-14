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
    
      <style>
 


.reject-btn1 {
    background-color: red;
    color: white;
    border: none;
    padding: 5px 15px;
    cursor: pointer;
    border-radius: 5px;
}

.button-container1 {
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




.reject-btn1:hover {
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


<!-- Back to Dashboard Button -->
	<button id="back-to-dashboard-2" class="back-btn3" >Back to
		Dashboard</button>


    <!-- My Network Section -->
    <div class="container1 mt-4">

      <!-- Small Navigation Tabs -->
        <ul class="networknav nav nav-tabs justify-content-center w-100" id="network-tabs">
            <li class="nav-item flex-fill">
                <a class="nav-link text-center" id="followers1" href="#" data-section="followers">Followers</a>
            </li>
         
            <li class="nav-item flex-fill">
                <a class="nav-link text-center" id="pending1" href="#" data-section="pending">Pending Requests</a>
            </li>
        </ul>

        <!-- Content Section for Followers & Following -->
        <div id="network-content" class="allusers mt-4">
                <!-- Card Container -->
    <div class="main-content">
    	
<!--         show the followers here
 -->        
         <div id="followers" class="tab-content">
         <h1>this is followers tab</h1>
             <div id="user-container1"></div>  <!-- This is where cards will be appended -->
         
            </div>

         
         
            <div id="pending" class="tab-content" style="display: none;">
                     <h1>this is Pending tab</h1>
            
                            <div id="user-container4"></div>  <!-- This is where cards will be appended -->

            </div>
        
    </div>
        </div>
        
                      <!-- Modal for User Profile -->
<div id="userProfileModal00" class="modal40" style="display:none;">
  <div class="modal40-content">
    <span class="close" onclick="closeUserProfileModal()">&times;</span>
    <div id="userProfileContent">
      <!-- User profile (profileforotheruser.jsp content) will load here dynamically -->
    </div>
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
        
    </div>
    
  
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
 
    

  
</body>
</html>