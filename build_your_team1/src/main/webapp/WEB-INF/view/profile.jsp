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


</style>

</head>
<body>

	<!-- Back to Dashboard Button -->
	<button id="back-to-dashboard" class="back-btn">Back to
		Dashboard</button>

	<div class="profile-container">

		<!-- Profile Header (Image + User Info) -->
		<div class="profile-header">

			
			
			<!-- Profile Image Section -->
<div class="profile-image" onclick="document.getElementById('upload-photo').click();">
    <img id="profile-pic" src="" alt="Animal Picture"  >
    
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
				<h2>John Doe</h2>
				<p>Email: johndoe@example.com</p>
				<p>⭐ Ratings: 4.8/5</p>
				<p>📍 Address: New York, USA</p>
			</div>

			<!-- Edit Profile Icon -->
			<i class="edit-icon" onclick="openEditProfile()">🖉</i>

			<!-- Logout Button -->
			<button class="logout-btn" onclick="logoutUser()">Logout</button>
		</div>
		<!-- Profile Sections -->


		<div class="profile-sections">
			<!-- Skills Section -->

			<div class="section">
				<h3>Skills</h3>
				<button class="add-btn" onclick="toggleSection('skill-form')">+
					Add Skill</button>

				<div class="form-section" id="skill-form">
					<input type="text" name="skillName" id="skillInput"
						placeholder="Enter Skill">
					<button class="add-btn1" id="addSkillBtn">Add Skill</button>

				</div>



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
				<button class="add-btn" onclick="toggleSection('project-form')">+
					Add Project</button>
				<div class="form-section" id="project-form">
					<input type="text" name="projectName" placeholder="Project Name">
					<textarea name="description" class="projectdescr"
						placeholder="Project Description"></textarea>
					<button class="add-btn1" id="addPorjectBtn">Add Project</button>
				</div>

				<!-- Display projects -->
				<div id="projectList"></div>
			</div>




			<!-- Certifications Section -->
			<div class="section">
				<h3>Certifications</h3>
				<button class="add-btn"
					onclick="toggleSection('certification-form')">+ Add
					Certification</button>
				<div class="form-section" id="certification-form">
					<input type="text" name="CertifcateName"
						placeholder="Certificate Name"> <input type="file"
						id="certificationFile" name="certificationFile">
					<button class="add-btn1" id="addCertification">Add
						Certification</button>

				</div>

				<!-- Display Certifications -->
				<!-- Display projects -->
				<div id="certificationList" style="margin-top: 11px;"></div>


			</div>

			<!-- Education Section -->
			<div class="section">
				<h3>Education</h3>
				<button class="add-btn" onclick="toggleSection('education-form')">+
					Add Education</button>
				<div class="form-section" id="education-form">
					<label>Education Level:</label> <select name="educationLevel">
						<option value="10th">10th</option>
						<option value="12th">12th</option>
						<option value="Graduation">Graduation</option>
						<option value="Post Graduation">Post Graduation</option>
					</select> <label>Status:</label> <select name="educationStatus">
						<option value="Completed">Completed</option>
						<option value="Pursuing">Pursuing</option>
					</select> <input type="number" name="percentage" placeholder="Percentage">
					<input type="number" name="passingYear" placeholder="Passing Year">

					<button class="add-btn1" onclick="addEducation()">Add
						Education</button>

				</div>
			</div>

		</div>

	</div>


	<!-- Edit Profile Modal -->


	<div id="editProfileModal" class="modal">
		<div class="modal-header">
			<h2>Edit Profile</h2>
			<button class="close-btn" onclick="closeEditProfile()">✖</button>
		</div>
		<div class="modal-content">
			<label>Name:</label> <input type="text" id="editName"
				placeholder="Enter Name"> <label>Email:</label> <input
				type="email" id="editEmail" placeholder="Enter Email"> <label>Mobile
				Number:</label> <input type="text" id="editMobile"
				placeholder="Enter Mobile Number"> <label>Gender:</label> <select
				id="editGender">
				<option value="">Select Gender</option>
				<option value="Male">Male</option>
				<option value="Female">Female</option>
				<option value="Other">Other</option>
			</select> <label>State:</label> <select id="state">
				<option>Select State</option>
				<option value="Maharashtra">Maharashtra</option>
				<option value="Karnataka">Karnataka</option>
				<option value="Delhi">Delhi</option>
				<option value="Gujarat">Gujarat</option>
				<option value="Tamil Nadu">Tamil Nadu</option>
				<option value="Uttar Pradesh">Uttar Pradesh</option>
				<option value="West Bengal">West Bengal</option>
			</select> <label>City:</label> <select id="city">
				<option>Select City</option>
			</select>

			<button class="save-btn">Save Changes</button>
		</div>
	</div>


	<script>
		document.getElementById("back-to-dashboard").addEventListener("click",
				function() {
					window.location.href = "<c:url value='/dashboard' />";
				});
	</script>


</body>
</html>