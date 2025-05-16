<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isELIgnored="false"%>
<html lang="en">


<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>

<%--  <link href="<c:url value='/Resource/css/style.css' />" rel="stylesheet"> --%>
<link href="<c:url value='/Resource/css/style1.css' />" rel="stylesheet">
<link href="<c:url value='/Resource/css/style3.css' />" rel="stylesheet">

<style>
/* Hide form sections initially */
.form-section {
	display: none;
	margin-top: 10px;
}

.form-section input, .form-section textarea, .form-section select {
	display: block;
	width: 100%;
	margin: 5px 0;
	padding: 8px;
}

.add-btn1 {
	margin-top: 10px;
	background-color: #d4f1d4;
}

#skillList {
	list-style: none;
	padding: 0;
	display: flex;
	flex-wrap: wrap;
	gap: 10px;
	margin-top: 20px;
}

#skillList li {
	background-color: #f0f0f0;
	padding: 8px 12px;
	border-radius: 5px;
	display: inline-block;
}

.project-item {
	border: 1px solid #ccc;
	border-radius: 8px;
	padding: 10px;
	margin: 10px 0;
	background-color: white;
	width: 100%;
	height: auto;
}

.project-name, .project-description {
	padding: 5px 0;
}

.project-name {
	font-size: 18px;
	font-weight: bold;
	color: #333;
}

.project-description {
	font-size: 16px;
	color: #666;
}

.projectdescr {
	height: 25vh;
}

.project-description {
	white-space: pre-line;
	font-weight: bold;
}

.certification-link {
	font-weight: bold;
	font-size: 20px;
}

.modal3 {
display:none;
   position: fixed;
  z-index: 1000;
  left: 38%;
  top: 50%;
  width: 100%;
  height: auto;
  max-width: 400px;
    
}

.modal3-content {
   
    transform: translate(-50%, -50%);
    background: #fff;
    padding: 15px;
    border-radius: 8px;
    max-width: 90%;
}

.close3-btn {
	background: red;
	color: white;
	padding: 5px 10px;
	border: none;
	cursor: pointer;

}

.education-item {
	border: 1px solid #ccc;
	border-radius: 8px;
	padding: 10px;
	margin: 10px 0;
	background-color: white;
	width: 100%;
	height: auto;
}


</style>

</head>
<body>


	<div class="profile-container00">

		<!-- Profile Header (Image + User Info) -->
		<div class="profile-header">

			
			
			<!-- Profile Image Section -->
<div class="profile-image" onclick="document.getElementById('upload-photo').click();">
    <img id="profile-pic1" src="" alt="Animal Picture"  >
    
    <input type="file" id="upload-photo" accept="image/*" style="display: none;" onchange="previewImage(event)">
    


</div>

<!-- Preview Image Button -->
    <button id="previewImageBtn" class="NewBtn"onclick="openImagePreview(event)">Preview Image</button>
    
            <!-- Image Preview Modal -->
<div id="imagePreviewModal" class="modal3" style="display: none;">
    <div class="modal3-content">
        <span class="close3-btn" onclick="closeImagePreview()">&times;</span>
        <img id="previewImage" src="" alt="Full Image" style="width: 100%; height: auto;">
    </div>
</div>


<%-- ${profile.imagePath} --%>

			<!-- User Info Section -->
			<div class="user-info">
				<h2>Name: ${otheruser.name}</h2>
				<p>Email: ${otheruser.email}</p>
				
<p><i class="fas fa-map-marker-alt"></i> Address: ${otheruser.city}, ${otheruser.state}</p>
			</div>

		</div>
		<!-- Profile Sections -->


		<div class="profile-sections">
			<!-- Skills Section -->

			<div class="section">
				<h3>Skills</h3>
				



				<!-- Display Skills -->
				<ul id="skillList">
					<c:forEach var="skill" items="${profile.skills}">
						<li>${skill.skillName}</li>
					</c:forEach>
				</ul>
			</div>


			<!-- Projects Section -->
			<div class="section">
				<h3>Projects</h3>
				

				<!-- Display projects -->
				<div id="projectList1"></div>
			</div>


			<!-- Certifications Section -->
			<div class="section">
				<h3>Certifications</h3>
				

				<!-- Display Certifications -->
				<!-- Display projects -->
				<div id="certificationList1" style="margin-top: 11px;"></div>


			</div>

			<!-- Education Section -->
			<div class="section">
				<h3>Education</h3>
				
				
				
				<!-- Display Education -->
				<div id="Educationsection1"></div>
			</div>

		</div>

	</div>


	<!-- Edit Profile Modal -->



	<script>
		document.getElementById("back-to-dashboard").addEventListener("click",
				function() {
					window.location.href = "<c:url value='/dashboard' />";
				});
	</script>


</body>
</html>