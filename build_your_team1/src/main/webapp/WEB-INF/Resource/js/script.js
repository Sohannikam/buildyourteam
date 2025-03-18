
document.addEventListener("DOMContentLoaded", function() {

	const sections = {
		profile: { btn: "profile-link", content: "dynamic-content", url: "profile" },
		findPartner: { btn: "findpartner", content: "findpartner-content", url: "findpart" },
		myNetwork: { btn: "Mynet", content: "Mynetworkcontent", url: "Mynet" },
		AddYourPost: { btn: "AddPost", content: "Add-Post", url: "ADDPOST" }
	};

	const dashboardContent = document.getElementById("dashboard-content");
	const footer = document.querySelector(".footer-content");

	function hideAllSections() {
		dashboardContent.style.display = "none";
		footer.style.display = "none";
		Object.values(sections).forEach(({ content }) => {
			document.getElementById(content).style.display = "none";
		});
	}

	function loadContent(sectionKey) {
		const { btn, content, url } = sections[sectionKey];
		document.getElementById(btn).addEventListener("click", function(event) {
			event.preventDefault();
			hideAllSections();

			fetch(url)
				.then(response => response.text())
				.then(data => {
					const section = document.getElementById(content);
					section.innerHTML = data;
					section.style.display = "block";

					// Attach back-to-dashboard functionality
					const backButton = document.getElementById("back-to-dashboard");

					if (backButton) {


						backButton.addEventListener("click", function() {
							console.log("back button touched succesfully")
							hideAllSections();
							dashboardContent.style.display = "block";
							footer.style.display = "block";
						});
					}
					const backButton1 = document.getElementById("back-to-dashboard-1");

					if (backButton1) {
						backButton1.addEventListener("click", function() {
							hideAllSections();
							dashboardContent.style.display = "block";
							footer.style.display = "block";
						});
					}

					const backButton2 = document.getElementById("back-to-dashboard-2");

					if (backButton2) {
						backButton2.addEventListener("click", function() {
							hideAllSections();
							dashboardContent.style.display = "block";
							footer.style.display = "block";
						});
					}


					// Call functions for specific sections
					if (sectionKey === "profile") {
						initializeStateCityDropdown();
						initialiseSkills();// ✅ Load skills immediately when profile loads

					}
					if (sectionKey == "findPartner") {
						fetchUsers();
					}
					if (sectionKey === "myNetwork") initializeNetworkTabs();
				})
				.catch(error => console.error(`Error loading ${sectionKey} page:`, error));
		});
	}

	Object.keys(sections).forEach(loadContent);
});

// ✅ Function to handle state-city dropdown AFTER profile.jsp loads
function initializeStateCityDropdown() {
	const stateDropdown = document.getElementById("state");
	const cityDropdown = document.getElementById("city");

	if (stateDropdown && cityDropdown) {
		const cityData = {
			"Maharashtra": ["Mumbai", "Pune", "Nagpur", "Nashik"],
			"Karnataka": ["Bangalore", "Mysore", "Hubli"],
			"Delhi": ["New Delhi"],
			"Gujarat": ["Ahmedabad", "Surat", "Vadodara"],
			"Tamil Nadu": ["Chennai", "Coimbatore", "Madurai"],
			"Uttar Pradesh": ["Lucknow", "Kanpur", "Varanasi"],
			"West Bengal": ["Kolkata", "Howrah", "Durgapur"]
		};

		stateDropdown.addEventListener("change", function() {
			let selectedState = this.value;
			let cities = cityData[selectedState] || [];

			cityDropdown.innerHTML = '<option>Select City</option>';
			cities.forEach(city => {
				let option = document.createElement("option");
				option.value = city;
				option.textContent = city;
				cityDropdown.appendChild(option);
			});
		});
	} else {
		console.error("State or City dropdown not found!");
	}
}


//profile section
function toggleSection(sectionId) {

	console.log("Toggling section: " + sectionId);

	var section = document.getElementById(sectionId);
	if (section.style.display === "none" || section.style.display === "") {
		section.style.display = "block"; // Show the section
	} else {
		section.style.display = "none"; // Hide the section
	}
}


//logout button

function logoutUser() {

	window.location.href = 'login'; // Redirect to login page
}

//dyanamic image upload

/*function previewImage(event) {
	const file = event.target.files[0];
	if (file) {
		const reader = new FileReader();
		reader.onload = function(e) {
			document.getElementById("profile-pic").src = e.target.result;
		};
		reader.readAsDataURL(file);
	}
}
*/

//Edit prfile


// Open Edit Profile Modal
function openEditProfile() {
	document.getElementById("editProfileModal").classList.add("active");
}

// Close Edit Profile Modal
function closeEditProfile() {
	document.getElementById("editProfileModal").classList.remove("active");
}


//handle user request
function sendRequest(userName) {
	alert("Request sent to " + userName);
	// You can add an AJAX request here to send data to the backend
}


//Apply Filter model

// Open Filter Modal
function openFilterModal() {
	document.getElementById("filterModal").style.display = "block";
}

// Close Filter Modal
function closeFilterModal() {
	document.getElementById("filterModal").style.display = "none";
}

// Apply Filters (Placeholder Function)
function applyFilters() {
	let location = document.getElementById("location").value;
	let skillsElement = document.getElementById("skills");
	if (!skillsElement) {
		console.error("Skills dropdown not found!");
		return;
	}

	let skills = Array.from(skillsElement.selectedOptions).map(option => option.value);
	let availability = document.querySelector('input[name="availability"]:checked')?.value;
	let experience = document.getElementById("experience").value;

	console.log("Filters Applied:", { location, skills, availability, experience });

	closeFilterModal();
}

//handle network tab here
function initializeNetworkTabs() {
	const tabs = document.querySelectorAll("#network-tabs .nav-link");
	const contentSections = document.querySelectorAll(".tab-content");

	console.log("Checking if document is fully loaded...");
	console.log("network-tabs:", document.getElementById("network-tabs")); // Should not be null
	console.log("All nav links:", document.querySelectorAll("#network-tabs .nav-link").length); // Should be > 0

	console.log("inside the Tab Switching content")
	console.log("Found tabs:", tabs.length); // Check if tabs are found
	console.log("content length is", contentSections.length)

	tabs.forEach(tab => {

		tab.addEventListener("click", function(e) {
			e.preventDefault();

			const sectionId = this.getAttribute("data-section");

			// Remove active class from all tabs and hide all content sections
			tabs.forEach(t => t.classList.remove("active"));
			contentSections.forEach(section => section.style.display = "none");

			// Add active class to the clicked tab and show the relevant content section
			this.classList.add("active");
			document.getElementById(sectionId).style.display = "block";


		});
	});
}


function initialiseSkills() {

	console.log("Fetching skills...");
	//Displalay skills on profile
	$.ajax({
		type: "GET",
		url: "getSkills", // Backend endpoint to fetch skills
		dataType: "json",
		success: function(response) {
			$("#skillList").empty(); // Clear old list
			console.log("Skills fetched:", response);

			$.each(response, function(index, skill) {
				$("#skillList").append("<li>" + skill.skillName + "</li>");
			});
		},
		error: function() {
			alert("Failed to load skills.");
		}
	});

	//Display Projects on profile
	$.ajax({
		type: "GET",
		url: "getProjects",
		dataType: "json",
		success: function(response) {

			console.log("inside success function");
			console.log(response);
			$("input[name='projectName']").val("");
			$("textarea[name='description']").val("");
			$("#projectList").empty();

			$.each(response, function(index, project) {
				console.log("inside loop of projects");
				console.log("project data", project)
				$("#projectList").append(
					"<div class='project-item'>" +
					"<div class='project-name'><strong>NAME:</strong> " + project.projectName + "</div>" +
					"<div class='project-description'><strong>DESCRIPTION:</strong> " + project.description + "</div>" +
					"</div>"
				);
			});
		},
		error: function() {
			alert("Failed to add project.");
		}
	});

	//Display Certification on Profile


	$.ajax({
		type: "GET",
		url: "GetCertification",
		processData: false,
		contentType: false,
		success: function(response) {

			console.log("inside sucess of certificate");
			$("input[name='CertifcateName']").val("");
			$("#certificationFile").val("");
			$("#certificationList").empty();

			$.each(response, function(index, cert) {

				console.log("inside sucess of certificate loop");
				$("#certificationList").append(
					"<li><a href='downloadCertification?cid=" + cert.cid + "' target='_blank' class='certification-link'>" +
					cert.certifcateName + "</a></li>"
				);
			});
		},
		error: function() {
			alert("Failed to add certification.");
		}
	});


	//Display image on profile 

	$.ajax({
		type: "GET",
		url: "displayImage",  // This URL should return the logged-in user's profile image
		success: function(response) {
			if (response) {
				document.getElementById("profile-pic").src = "data:image/jpeg;base64," + response.image;
			}
		},
		error: function() {
			console.log("No profile image found.");
		}
	});


	// ✅ Add Skill Functionality
	$("#addSkillBtn").off("click").on("click", function() {
		var skillName = $("#skillInput").val().trim();
		if (skillName === "") {
			alert("Please enter a skill.");
			return;
		}

		$.ajax({
			type: "POST",
			url: "add",
			data: { skillName: skillName },
			dataType: "json",
			success: function(response) {
				$("#skillInput").val("");
				$("#skillList").empty();
				console.log(response);

				$.each(response, function(index, skill) {

					$("#skillList").append("<li>" + skill.skillName + "</li>");
				});
			},
			error: function() {
				alert("Failed to add skill.");
			}
		});
	});

	// ✅ Add Project Functionality
	$("#addPorjectBtn").off("click").on("click", function() {
		var projectName = $("input[name='projectName']").val().trim();
		var projectDescription = $("textarea[name='description']").val().trim();

		if (projectName === "" || projectDescription === "") {
			alert("Please enter project name and description.");
			return;
		}

		$.ajax({
			type: "POST",
			url: "addProject",
			data: { projectName: projectName, projectDescription: projectDescription },
			dataType: "json",
			success: function(response) {

				console.log("inside success function");
				console.log(response);
				$("input[name='projectName']").val("");
				$("textarea[name='description']").val("");
				$("#projectList").empty();

				$.each(response, function(index, project) {
					console.log("inside loop of projects");
					console.log("project data", project)
					$("#projectList").append(
						"<div class='project-item'>" +
						"<div class='project-name'><strong>NAME:</strong> " + project.projectName + "</div>" +
						"<div class='project-description'><strong>DESCRIPTION:</strong> " + project.description + "</div>" +
						"</div>"
					);
				});
			},
			error: function() {
				alert("Failed to add project.");
			}
		});
	});


	// ✅ Add Certification Functionality
	$("#addCertification").off("click").on("click", function() {

		console.log("inside certification script")
		var formData = new FormData();
		var certificateFile = $("#certificationFile")[0].files[0];
		var certificateName = $("input[name='CertifcateName']").val().trim();

		if (!certificateName || !certificateFile) {
			alert("Please enter certificate name and choose a file.");
			return;
		}

		formData.append("CertifcateName", certificateName);
		formData.append("certificationFile", certificateFile);

		$.ajax({
			type: "POST",
			url: "addCertification",
			data: formData,
			dataType: "json",
			processData: false,
			contentType: false,

			success: function(response) {

				console.log("inside sucess of certificate");
				$("input[name='CertifcateName']").val("");
				$("#certificationFile").val("");
				$("#certificationList").empty();

				$.each(response, function(index, cert) {

					console.log("inside sucess of certificate loop");
					$("#certificationList").append(
						"<li> <a href='downloadCertification?cid=" + cert.cid + "' target='_blank' class='certification-link'>" +
						cert.certifcateName + "</a></li>"
					);
				});
			},
			error: function() {
				alert("Failed to add certification.");
			}
		});
	});


}


function previewImage(event) {

	var file = event.target.files[0];
	var reader = new FileReader();

	reader.onload = function() {
		var imageUrl = reader.result;

		// Display the uploaded image in the profile picture
		document.getElementById("profile-pic").src = imageUrl;

		// Create FormData to send the file to the server

		var formData = new FormData();
		formData.append("profilePicture", file);

		// AJAX request to upload the image
		$.ajax({
			type: "POST",
			url: "uploadImage",
			data: formData,
			dataType: "json",
			contentType: false,
			processData: false,
			success: function(response) {
				// Set the uploaded image as the profile picture
				alert("image uploaded succesfully");
				document.getElementById("profile-pic").src = "data:image/jpeg;base64," + response.image;
			},
			error: function(xhr, status, error) {
				console.log("Error Details:", xhr.responseText);
				alert("Error uploading image: " + xhr.responseText);
			}
		});
	};

	reader.readAsDataURL(file);
}

function openImagePreview(event) {
	event.stopPropagation(); // Prevents triggering file input click
	const profilePic = document.getElementById("profile-pic").src;

	if (profilePic) {
		document.getElementById("previewImage").src = profilePic;
		document.getElementById("imagePreviewModal").style.display = "block";
	}
}

function closeImagePreview() {
	document.getElementById("imagePreviewModal").style.display = "none";
}




//Fetch all users with skills image and other data

function fetchUsers() {
	console.log("inside all users fetching");
	$.ajax({
		url: "userslist",
		type: "GET",
		dataType: "json",
		success: function(users) {
			$("#user-container").empty(); // Clear existing users
			users.forEach(user => {
				fetchUserImage(user.id, function(imageData) {
					fetchPendingRequests(function(pendingRequests) {
						fetchAllRequests(function(allRequests) {
						let skillsList = "No skills added";
						if (user.skills && Array.isArray(user.skills) && user.skills.length > 0) {
							skillsList = user.skills.join(", ");
						}

						let profileImage = imageData ? `data:image/jpeg;base64,${imageData}` : "default-profile.png";


						let currentUserId = document.getElementById('currentUserId').value;
						// Determine if the user has a pending request

						
							

							let requestStatus = allRequests.find(req =>
								(req.senderId ===  Number(currentUserId) && req.receiverId === user.id) ||
								(req.senderId === user.id && req.receiverId ===  Number(currentUserId))
							)?.status;

							console.log("Request Status:", requestStatus);
						

						/* console.log("Pending Requests:", JSON.stringify(pendingRequests, null, 2));*/

						let isRequestReceived = pendingRequests.some(req => req.senderId === user.id);

						let buttonHtml = "";


						if (isRequestReceived) {

							pendingRequests.forEach(req => {
								console.log("Sender Id:", req?.senderId);
								console.log("Receiver Id:", req?.receiverId);
								console.log("staus", req?.status);
							});




							console.log("loged in user id is" + currentUserId);
							console.log("sending in user id is" + user.id);
							// Find the requestId from the pendingRequests array
							let requestId = pendingRequests.find(req => req.senderId === user.id && req.receiverId === Number(currentUserId))?.id;

							// Fixed here!

							console.log("Request ID:", requestId);
							console.log("inside isRequestReceived of script");
							buttonHtml = `
                            <div class="button-container" data-request-id="${requestId}">
                                <button class="accept-btn" onclick="updateRequestStatus(${requestId}, 'Accepted')">Accept</button>
                                <button class="reject-btn" onclick="updateRequestStatusReject(${requestId})">Reject</button>
                                </div>`;
						}



						else if (requestStatus === "Pending") {

							console.log("inside pending requestStatus");
							buttonHtml = `<button id="requestButton-${user.id}" class="send-request-btn" disabled>Pending</button>`;

						}
						else if (requestStatus === "Accepted") {
							console.log("inside Accepted requestStatus");
							buttonHtml = `<button class="send-request-btn">Message</button>`;
						}

						else {
							console.log("inside sendRequest button script");
							buttonHtml = `<button id="requestButton-${user.id}" class="send-request-btn" onclick="sendRequest(${user.id})">Send Request</button>`;
						}
						
						


						let userCard = `
                            <div class="user-card">
                            	${buttonHtml}
                                <img src="${profileImage}" alt="Profile Picture" class="profile-pic">
                                <div class="user-info">
                                    <h3>${user.name}</h3>
                                    <p>Skills: ${skillsList}</p>
                                    
                                        
                                    
                                </div>
                            </div>`;
						$("#user-container").append(userCard);
						});
					});
				});
			});
		},
		error: function(error) {
			console.log("Error fetching users:", error);
		}
	});
}

function fetchUserImage(userId, callback) {
	console.log("inside user image fetching");
	$.ajax({
		url: `displayImage1/${userId}`,
		type: "GET",
		dataType: "json",
		success: function(response) {
			callback(response.image);
		},
		error: function() {
			callback(null);
		}
	});
}

// Function to fetch pending requests for the logged-in user
function fetchPendingRequests(callback) {
	$.ajax({
		url: "pending",
		type: "GET",
		dataType: "json",
		success: function(response) {
			console.log(response);
			callback(response); // returns a list of pending requests
		},
		error: function() {
			callback([]); // No pending requests
		}
	});
}

// Function to get request status for a user from the list of pending requests
function getRequestStatus(receiverId1) {
	let status = null;

	$.ajax({
		url: `checkStatus/${receiverId1}`,
		type: "GET",
		dataType: "json",
		async: false,  // For returning value from AJAX
		success: function(response) {
			console.log(response); // Response contains multiple objects

			// Loop through the array of objects
			response.forEach(request => {

				console.log("request status for user is " + request.status);
				status = request.status;

			});
		},
		error: function() {
			alert("Failed to get request status");
		}
	});

	return status;  // Return the status from here
}


// Function to send a connection request
function sendRequest(receiverId) {
	$.ajax({
		type: "POST",
		url: `send/${receiverId}`,
		dataType: "json",
		success: function() {
			console.log("inside success of ajax for sedning request");
			$("#requestButton-" + receiverId).text("Pending").prop("disabled", true);
		},
		error: function() {
			alert("Error sending request.");
		}
	});
}

// Function to accept or reject a request
function updateRequestStatus(requestId, status) {
	$.ajax({
		type: "PUT",
		url: `update/${requestId}/${status}`,
		dataType: "json",
		success: function(response) {
			console.log(response.message); // Success message from backend
			
			// Change the button to 'Message' without refreshing
			const buttonContainer1 = document.querySelector(`.button-container[data-request-id="${requestId}"]`);
			if (buttonContainer1) {
				console.log("inside of buttonContainer1");
				buttonContainer1.innerHTML = `<button class="send-request-btn">Message</button>`;
			}
			
		},
		error: function() {
			console.error("Error:", error);
			alert("Error updating request status.");
		}
	});
}

function updateRequestStatusReject(requestId) {
	$.ajax({
		type: "PUT",
		url: `Reject/${requestId}`,
		dataType: "json",
		success: function(response) {

			let userId = response.userId;
			console.log("Requested UserId is"+userId);

			// Change the button container to 'Send Request' button
			// Target the button container and replace its content with 'Send Request' button
			
			const buttonContainer = document.querySelector(`.button-container[data-request-id="${requestId}"]`);
			
			if(buttonContainer)
			{
				
buttonContainer.innerHTML = `<button id="requestButton-${userId}" class="send-request-btn12" onclick="sendRequest(${userId})">Send Request</button>`;
			
			}
						
		},
		error: function() {
			console.error("Error:", error);
			alert("Error updating request status.");
		}
	});
}




function fetchAllRequests(callback) {
	$.ajax({
		url: "fetchAllRequests", // Create a new servlet to fetch all requests
		type: "GET",
		dataType: "json",
		success: function(allRequests) {
			console.log(allRequests);
			callback(allRequests); // Return all requests to the main function
		},
		error: function(error) {
			console.error("Error fetching all requests:", error);
		}
	});
}




