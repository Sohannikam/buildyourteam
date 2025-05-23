<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<%@page isELIgnored="false"%>
<head>
<meta http-equiv="Cross-Origin-Resource-Policy" content="cross-origin">

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Team Builder Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Bootstrap CSS -->
   <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="<c:url value='/Resource/css/style.css' />" rel="stylesheet">
    <link href="<c:url value='/Resource/css/style1.css' />" rel="stylesheet">
    <!-- Croppie CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/croppie/2.6.4/croppie.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <%-- <%@include file="./base.jsp" %> --%>
   
</head>
<body>
   
<input type="hidden" id="currentUserId" value="${sessionScope.UserId}">

   <header>
    <nav class="navbar navbar-expand-lg navbar-dark" style="padding-bottom: 4px;">
        <div class="container-fluid">

             <!-- Logo Section -->
             <a class="navbar-brand d-flex align-items-center" href="#">
                <img src="<c:url value="/Resource/images/logo.jpeg"/>" alt="Teams Logo" class="logo">			
                <span class="ms-2 d-inline-block" style="font-size: 1rem;">GROW TOGETHER</span> <!-- Optional text next to the logo -->
            </a>
            
            <!-- Toggler button for mobile view -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse justify-content-center-end" id="navbarNav">
                <div class="nav-box">
                <ul class="navbar-nav">
                    <li class="nav-iteem ms-5" ><a class="nav-link" id="profile-link" href="#"><b>PROFILE</b></a></li>
                    <li class="nav-item ms-5"><a class="nav-link" id="findpartner" href="#"><b>USERS</b></a></li>
                    <li class="nav-item ms-5"><a class="nav-link" id="Mynet" href="#"><b>MY NETWORK</b></a></li>
                    <li class="nav-item ms-5"><a class="nav-link" id="AddPost" href="#"><b>ADD POST</b></a></li>
                    
                </ul>
            </div>
        </div>
        </div>
    </nav>
</header>

    
    <main class="container mt-4 main-content">

        <div id="dashboard-content" style="margin-top:70px;" >
           <h1>hello,${user.name}</h1>

      <section class="card-container" id="ideas">
    <div id="blog-container"></div>
</section>

       </div>

       <!-- Profile Page Content (Initially Hidden) -->
       <div id="dynamic-content" style="display: none;"></div>
       
        <!-- Findyourpartner Page Content (Initially Hidden) -->
       <div id="findpartner-content" style="display: none;"></div>
       
       <!-- Mynetwork Page Content (Initially Hidden) -->
       <div id="Mynetworkcontent" style="display: none;"></div>
       
       <!-- AddPost Page Content (Initially Hidden) -->
       <div id="Add-Post" style="display: none;"></div>

    </main>
    
    
    <footer class="footer-content">
        <div class="container">
            <p>Follow us:</p>
            <ul class="list-inline">
                <li class="list-inline-item"><a href="#">Instagram</a></li>
                <li class="list-inline-item"><a href="#">Facebook</a></li>
                <li class="list-inline-item"><a href="mailto:contact@teambuilder.com">Email</a></li>
                <li class="list-inline-item"><a href="tel:+123456789">Contact</a></li>
            </ul>
        </div>
    </footer>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>




<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1.5.1/dist/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>

 <!-- Load EmojiButton library first -->
<!-- <script src="https://unpkg.com/@joeattardi/emoji-button@4.6.4/dist/index.js" type="module"></script> -->

<script src="<c:url value='/Resource/js/script.js'/>" ></script>
<!-- Croppie JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/croppie/2.6.4/croppie.min.js"></script>

    
</body>
</html>