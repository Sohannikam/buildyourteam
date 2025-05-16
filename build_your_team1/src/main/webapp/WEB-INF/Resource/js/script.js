const sections = {
		profile: { btn: "profile-link", content: "dynamic-content", url: "profile" },
		findPartner: { btn: "findpartner", content: "findpartner-content", url: "findpart" },
		myNetwork: { btn: "Mynet", content: "Mynetworkcontent", url: "Mynet" },
		AddYourPost: { btn: "AddPost", content: "Add-Post", url: "ADDPOST" }
	};

	const dashboardContent = document.getElementById("dashboard-content");
	const footer = document.querySelector(".footer-content");
	
	
document.addEventListener("DOMContentLoaded", function() {
	
	function hideAllSections() {
		dashboardContent.style.display = "none";
		footer.style.display = "none";
		Object.values(sections).forEach(({ content }) => {
			document.getElementById(content).style.display = "none";
		});
	}

	function loadContent(sectionKey, pushToHistory = false) {
	const { content, url } = sections[sectionKey];
	hideAllSections();

	fetch(url)
		.then(response => response.text())
		.then(data => {
			const section = document.getElementById(content);
			section.innerHTML = data;
			section.style.display = "block";

			if (pushToHistory) {
				history.pushState({ sectionKey }, "", `#${sectionKey}`);
			}

			// Attach back buttons
			["back-to-dashboard", "back-to-dashboard-1", "back-to-dashboard-2","back-to-dashboard-6"].forEach(id => {
				const btn = document.getElementById(id);
				if (btn) {
					btn.addEventListener("click", function () {
						hideAllSections();
						dashboardContent.style.display = "block";
						footer.style.display = "block";
					});
				}
			});

			// Section-specific scripts
			if (sectionKey === "profile") {
				initializeStateCityDropdown();
				initialiseSkills();
			}
			if (sectionKey === "findPartner") {
				fetchUsers();
				populateLocationDropdown();
			}
			if (sectionKey === "myNetwork") {
				initializeNetworkTabs();
				fetchAcceptedUsersformy();
			}
			
			if (sectionKey === "AddYourPost") {
				console.log("inside when section key is ADDPOST");
				CreatPost();
			}
		})
		.catch(error => console.error(`Error loading ${sectionKey} page:`, error));
}

// 🔙 Enable back/forward browser navigation
window.addEventListener('popstate', function (event) {
	const state = event.state;
	if (state && state.sectionKey) {
		loadContent(state.sectionKey, false); // Don't push again
	} else {
		hideAllSections();
		dashboardContent.style.display = "block";
		footer.style.display = "block";
	}
});

// 🖱️ Attach click handlers to all buttons
Object.keys(sections).forEach(sectionKey => {
	const { btn } = sections[sectionKey];
	document.getElementById(btn).addEventListener("click", function (e) {
		e.preventDefault();
		loadContent(sectionKey, true); // true = push to history
	});
});

});


//Fetch all blogs from the database
console.log("script called agian");

$(document).ready(function () {
    console.log("inside of fetch blogs again");

    $.ajax({
        url: "getAllBlogs",
        type: "GET",
        dataType: "json",
        success: function (blogs) {
            console.log("blogs accessed successfully");
            let container = $('#blog-container');
            container.empty();

            blogs.forEach((blog, index) => {
                const blogId = `blog-${index}`;
                const base64BlogImage = blog.image ? `data:image/jpeg;base64,${blog.image}` : '';
                const profileImage = 'default-avatar.png'; // default image first

                const card = `
                    <div class="card p-4 mb-3" id="${blogId}">
                        <div class="d-flex align-items-center mb-2">
                            <img src="${profileImage}" alt="Profile" class="rounded-circle user-image" width="50" height="50">
                            <h5 class="ms-3">${blog.user.name}</h5>
                        </div>
                        
                        <h1 class="blog-title">${blog.title}</h1>

                        <div class="blog-body">
                            ${base64BlogImage ? `<img src="${base64BlogImage}" class="blog-img"/>` : ''}
                            <div class="blog-desc"><h5>${blog.description}</h5></div>
                        </div>

                        <p class="text-muted"><small>Posted on: ${new Date(blog.createdDate).toLocaleString()}</small></p>
                    </div>
                `;

                container.append(card);

                // Fetch and update user image asynchronously
                if (blog.user && blog.user.id) {
                    fetchUserImage(blog.user.id, function (userImage) {
                        const finalImage = userImage ? `data:image/jpeg;base64,${userImage}` : 'default-avatar.png';
                        $(`#${blogId} .user-image`).attr("src", finalImage);
                    });
                }
            });
        },
        error: function (err) {
            console.error("Failed to load blogs", err);
        }
    });
});


/*
function loadEmojiButton(callback) {
    if (window.EmojiButton) {
        callback();
        return;
    }

    const script = document.createElement('script');
    script.src = 'https://unpkg.com/@joeattardi/emoji-button@4.6.4/dist/index.js';
    script.type='module';
    script.onload = () => {
        console.log("✅ EmojiButton loaded dynamically");
        callback();
    };
    script.onerror = () => {
        console.error("❌ Failed to load EmojiButton script");
    };
    document.head.appendChild(script);
}

// Call it like this
loadEmojiButton(() => {
    // use EmojiButton here
    initEmojiPickers(); // your function that uses new EmojiButton()
});

	
function initEmojiPickers() {
  function setupEmojiPicker(buttonId, inputId) {
    const button = document.getElementById(buttonId);
    const input = document.getElementById(inputId);
    const picker = new EmojiButton({ position: 'top-end' });

    picker.on('emoji', emoji => {
      const start = input.selectionStart;
      const end = input.selectionEnd;
      const text = input.value;

      input.value = text.slice(0, start) + emoji + text.slice(end);
      input.focus();
      input.selectionEnd = start + emoji.length;
    });

    button.addEventListener('click', () => {
      picker.togglePicker(button);
    });
  }

  // Attach emoji pickers
  setupEmojiPicker('emoji-btn-title', 'title');
  setupEmojiPicker('emoji-btn-desc', 'description');
}

*/


//fuction to show citties in the search filter model
function populateLocationDropdown() {
	
	console.log("inside of populatedLocationDropDown");
    const locationDropdown = document.getElementById("location");

    // Clear existing options
    locationDropdown.innerHTML = '<option value="">Select Location</option>';

    // Your state-wise city data
    const cityData = {
        "Maharashtra": ["Mumbai", "Pune", "Nagpur", "Nashik"],
        "Karnataka": ["Bangalore", "Mysore", "Hubli"],
        "Delhi": ["New Delhi"],
        "Gujarat": ["Ahmedabad", "Surat", "Vadodara"],
        "Tamil Nadu": ["Chennai", "Coimbatore", "Madurai"],
        "Uttar Pradesh": ["Lucknow", "Kanpur", "Varanasi"],
        "West Bengal": ["Kolkata", "Howrah", "Durgapur"]
    };

    // Flatten all cities into a single list
    const allCities = Object.values(cityData).flat(); 

    // Add cities to the dropdown
    allCities.forEach(city => {
        let option = document.createElement("option");
        option.value = city;
        option.textContent = city;
        locationDropdown.appendChild(option);
    });
}



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
	
	//get educatio after page reload
$.ajax({
    type: "GET",
    url: "GetEducation", // This should be the correct endpoint for fetching education details
  	dataType: "json",
    success: function(response) {
        console.log("Inside success of education retrieval");

        // Clear the previous education section content
        $("#Educationsection").empty();

        $.each(response, function(index, education) {
            console.log("Inside success loop of education retrieval");

            // Append education details dynamically
             $("#Educationsection").append(
                    "<div class='education-item'>" +
                    "<div><strong>Level:</strong> " + education.formattedEducationLevel + "</div>" +
                    "<div><strong>Status:</strong> " + education.status + "</div>" +
                    "<div><strong>Percentage:</strong> " + education.percentage + "%</div>" +
                    "<div><strong>Passing Year:</strong> " + education.passingYear + "</div>" +
                    "<div><strong>Branch:</strong> " + education.branch + "</div>" +
                    "</div>"
                );
        });
    },
    error: function() {
        alert("Failed to retrieve education details.");
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
	
	
	//add education
	
	$("#addEducationBtn").off("click").on("click", function() {
    var educationLevel = $("select[name='educationLevel'] option:selected").attr("data-enum");
    var educationStatus = $("select[name='educationStatus']").val();
    var percentage = $("input[name='percentage']").val().trim();
    var passingYear = $("input[name='passingYear']").val().trim();
    var Branch = $("input[name='Branch']").val().trim();

    if (educationLevel === "" || educationStatus === "" || percentage === "" || passingYear === "") {
        alert("Please fill all education details.");
        return;
    }

    $.ajax({
        type: "POST",
        url: "addEducation",
        data: {
            educationLevel: educationLevel,
            status: educationStatus,
            percentage: percentage,
            passingYear: passingYear,
            Branch:Branch
           
        },
        dataType: "json",
        success: function(response) {
            console.log("Education added successfully:", response);

            // Clear the input fields
            $("select[name='educationLevel']").val("");
            $("select[name='educationStatus']").val("");
            $("input[name='percentage']").val("");
            $("input[name='passingYear']").val("");
             $("input[name='Branch']").val("");

            $("#Educationsection").empty();

            // Append the newly added education data
            $.each(response, function(index, education) {
				
				 // Print each education object in console
    console.log("Education Entry:", education);
                $("#Educationsection").append(
                    "<div class='education-item'>" +
                    "<div><strong>Level:</strong> " + education.formattedEducationLevel + "</div>" +
                    "<div><strong>Status:</strong> " + education.status + "</div>" +
                    "<div><strong>Percentage:</strong> " + education.percentage + "%</div>" +
                    "<div><strong>Passing Year:</strong> " + education.passingYear + "</div>" +
                    "<div><strong>Branch:</strong> " + education.branch + "</div>" +
                    "</div>"
                );
            });
        },
        error: function() {
            alert("Failed to add education.");
        }
    });
});



// script to update the user profile details

$("#saveProfileBtn").off("click").on("click", function() {
	
	console.log("inside saveprofilebtn");
    var name = $("#editName").val().trim();
    var email = $("#editEmail").val().trim();
    var mobile = $("#editMobile").val().trim();
    var gender = $("#editGender").val();
    var state = $("#state").val();
    var city = $("#city").val();

    // Validation
    if (name === "" || email === "" || mobile === "" || gender === "" || state === "" || city === "") {
        alert("Please fill all profile details.");
        return;
    }

$.ajax({
    type: "PUT",
    url: "updateProfile",
    contentType: "application/json",  // Telling Spring that we are sending JSON
    data: JSON.stringify({
        name: name,
        email: email,
        mobile: mobile,
        gender: gender,
        state: state,
        city: city
    }),
    dataType: "json",
    success: function(response) {
        console.log("Profile updated successfully:", response);
        if (response.status === "success") {
            alert("Profile updated successfully!");
            
             // Update the UI dynamically without refresh
            $(".user-info h2").text("Name: "+name);
            $(".user-info p:eq(0)").text("Email: " + email);
            $(".user-info p:eq(1)").text("\uD83D\uDCCD Address:" + city + ", " + state);
           // Close modal
                closeEditProfile();
        } else {
            alert("Profile update failed. Try again.");
        }
    },
    error: function() {
        alert("Failed to update profile.");
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
	
		// Show loading message before AJAX starts
	$("#user-container").html(`
    <div class="loading-message" style="
        text-align: center;
        padding: 20px;
        font-size: 18px;
        color: #007bff;
        animation: pulse 1.5s infinite;
    ">
      <h1>&#8987; Loading users, please wait...<h1>
    </div>

    <style>
        @keyframes pulse {
            0% { opacity: 1; }
            50% { opacity: 0.5; }
            100% { opacity: 1; }
        }
    </style>
`);

	
	$.ajax({
		url: "userslist",
		type: "GET",
		dataType: "json",
		success: function(users) {
			$("#user-container").empty(); // Clear existing users

			let userPromises = users.map((user, index) => {
				return new Promise(resolve => {
					fetchUserImage(user.id, function(imageData) {
						fetchPendingRequests(function(pendingRequests) {
							fetchAllRequests(function(allRequests) {
								let skillsList = user.skills && user.skills.length > 0 ? user.skills.join(", ") : "No skills added";
								let profileImage = imageData ? `data:image/jpeg;base64,${imageData}` : "default-profile.png";
								let currentUserId = Number(document.getElementById('currentUserId').value);

								let requestStatus = allRequests.find(req =>
									(req.senderId === currentUserId && req.receiverId === user.id) ||
									(req.senderId === user.id && req.receiverId === currentUserId)
								)?.status;

								let isRequestReceived = pendingRequests.some(req => req.senderId === user.id);
								let buttonHtml = "";

								if (isRequestReceived) {
									let requestId = pendingRequests.find(req => req.senderId === user.id && req.receiverId === currentUserId)?.id;
									buttonHtml = `
                                    <div class="button-container" data-request-id="${requestId}" 
                                                                  data-user-id="${user.id}" 
             													  data-LoggedInUser-id="${currentUserId}" 
             													  data-user-name="${user.name}" 
             													  data-user-image="${profileImage}">

                                <button class="accept-btn" onclick="updateRequestStatus(${requestId}, 'Accepted')">Accept</button>
                                <button class="reject-btn" onclick="updateRequestStatusReject(${requestId})">Reject</button>
                            </div>`;
								} else if (requestStatus === "Pending") {
                                    buttonHtml = `<button id="requestButton-${user.id}" class="send-request-btn" disabled style="background-color: #d3d3d3;  cursor: not-allowed;">Pending</button>`;
								} else if (requestStatus === "Accepted") {
									buttonHtml = `<button class="send-request-btn" onclick="openMessageModal(${user.id}, '${user.name}', '${profileImage}')">Message</button>`;

								} else {
									buttonHtml = `<button id="requestButton-${user.id}" class="send-request-btn" onclick="sendRequest(${user.id})">Send Request</button>`;
								}
							
								
								let userCard = `
                        <div class="user-card">
                            ${buttonHtml}
                            <img src="${profileImage}" alt="Profile Picture" class="profile-pic"  style="cursor: pointer;"
            onclick="redirectToUserProfile(${user.id})">
                            <div class="user-info">
                                <h3>${user.name}</h3>
                                <p>Skills: ${skillsList}</p>
                                ${user.city ? `<p>City: ${user.city}</p>` : ``}
                            </div>
                        </div>`;

								resolve({ index, userCard }); // Preserve backend order using index
							});
						});
					});
				});
			});

			Promise.all(userPromises).then(userCards => {
				userCards.sort((a, b) => a.index - b.index); // Ensure the order matches the backend response
				userCards.forEach(userData => {
					$("#user-container").append(userData.userCard);
				});
			});

		},
		error: function(error) {
			console.log("Error fetching users:", error);
		}
	});
}


function redirectToUserProfile(userId) {
    console.log("Opening user profile for userId:", userId);

    // 1. Open the modal
    $("#userProfileModal").css("display", "block");

    // 2. Load basic JSP (profileforotheruser.jsp) inside the modal
    $("#userProfileContent").load("loadUserProfile?userId=" + userId, function() {
        
        console.log("ProfileForOtherUser JSP loaded, now fetching skills...");

        // 3. After loading the JSP, call AJAX to fetch skills for that user
        getimageforotheruser(userId);
        fetchSkillsForOtherUser(userId);
        getPorjectForOtherUser(userId);
        getCertificationforother(userId);
        getEducaationforotheruser(userId);

        // Similarly you can later call fetchProjectsForOtherUser(userId), fetchEducationForOtherUser(userId), etc.
    });
}

function redirectToUserProfileformynetwork(userId) {
    console.log("Opening user profile for userId:", userId);

    // 1. Open the modal
    $("#userProfileModal00").css("display", "block");

    // 2. Load basic JSP (profileforotheruser.jsp) inside the modal
    $("#userProfileContent").load("loadUserProfile?userId=" + userId, function() {
        
        console.log("ProfileForOtherUser JSP loaded, now fetching skills...");

        // 3. After loading the JSP, call AJAX to fetch skills for that user
        getimageforotheruser(userId);
        fetchSkillsForOtherUser(userId);
        getPorjectForOtherUser(userId);
        getCertificationforother(userId);
        getEducaationforotheruser(userId);

        // Similarly you can later call fetchProjectsForOtherUser(userId), fetchEducationForOtherUser(userId), etc.
    });
}
function closeUserProfileModal() {
    $("#userProfileModal").css("display", "none");
}

function getimageforotheruser(userId)
{
	$.ajax({
		type: "GET",
		data: { userId: userId },
		url: "displayImageforotheruser",  // This URL should return the logged-in user's profile image
		success: function(response) {
			if (response) {
				document.getElementById("profile-pic1").src = "data:image/jpeg;base64," + response.image;
			}
		},
		error: function() {
			console.log("No profile image found.");
		}
	});
}

//fetch skills for other users 
function fetchSkillsForOtherUser(userId) {
    console.log("Fetching skills for userId:", userId);

    $.ajax({
        type: "GET",
        url: "getSkillsForOtherUser",
        data: { userId: userId },
        dataType: "json",
        success: function(response) {
            console.log("Skills fetched for other user:", response);
            
              // Set name/email/etc manually in JS

            $("#skillList").empty(); // Clear old skills

            if (response.length === 0) {
                $("#skillList").append("<li>No skills added yet.</li>");
            } else {
                $.each(response, function(index, skill) {
                    $("#skillList").append("<li>" + skill.skillName + "</li>");
                });
            }
        },
        error: function() {
            alert("Failed to load skills for user.");
        }
    });
}


//fetch project for other users
function getPorjectForOtherUser(userId){
	//Display Projects on profile
	$.ajax({
		type: "GET",
		url: "getProjectsForOtherUsers",
		data: { userId: userId },
		dataType: "json",
		success: function(response) {

			console.log("inside success function of getProjects for other users");
			console.log(response);
			/*$("input[name='projectName']").val("");
			$("textarea[name='description']").val("");
			$("#projectList1").empty();*/

			$.each(response, function(index, project) {
				console.log("inside loop of getProjectforotherusers");
				console.log("project data", project)
				$("#projectList1").append(
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

}

function getCertificationforother(userId)
{
	
		$.ajax({
		type: "GET",
		url: "GetCertificationforother",
		data: { userId: userId },
		/*processData: false,
		contentType: false,*/
		success: function(response) {

			console.log("inside sucess of certificate");
		

			$.each(response, function(index, cert) {
					
console.log("Formatted response:", JSON.stringify(response, null, 2));
				console.log("inside sucess of certificate loop");
				$("#certificationList1").append(
"<li><a href='downloadCertificationforother?cid=" + cert.cid + "&pid=" + cert.pid + "' target='_blank' class='certification-link'>" +
					cert.certifcateName + "</a></li>"
				);
			});
		},
		error: function() {
			alert("Failed to add certification.");
		}
	});
	
}

function getEducaationforotheruser(userId)
{
	$.ajax({
    type: "GET",
    url: "GetEducationforotheruser", // This should be the correct endpoint for fetching education details
  	dataType: "json",
  	data: { userId: userId },
    success: function(response) {
        console.log("Inside success of education retrieval");

        // Clear the previous education section content
        $("#Educationsection1").empty();

        $.each(response, function(index, education) {
            console.log("Inside success loop of education retrieval");

            // Append education details dynamically
             $("#Educationsection1").append(
                    "<div class='education-item'>" +
                    "<div><strong>Level:</strong> " + education.formattedEducationLevel + "</div>" +
                    "<div><strong>Status:</strong> " + education.status + "</div>" +
                    "<div><strong>Percentage:</strong> " + education.percentage + "%</div>" +
                    "<div><strong>Passing Year:</strong> " + education.passingYear + "</div>" +
                    "<div><strong>Branch:</strong> " + education.branch + "</div>" +
                    "</div>"
                );
        });
    },
    error: function() {
        alert("Failed to retrieve education details.");
    }
});
}

//fetch all users with Request status is Accepted

//Fetch all users with skills image and other data
function FetchAcceptedUsers() {
	console.log("inside all users fetching");
	
		// Show loading message before AJAX starts
	$("#user-container1").html(`
    <div class="loading-message" style="
        text-align: center;
        padding: 20px;
        font-size: 18px;
        color: #007bff;
        animation: pulse 1.5s infinite;
    ">
      <h1>&#8987; Loading users, please wait...<h1>
    </div>

    <style>
        @keyframes pulse {
            0% { opacity: 1; }
            50% { opacity: 0.5; }
            100% { opacity: 1; }
        }
    </style>
`);


	$.ajax({
		url: "acceptedList",
		type: "GET",
		dataType: "json",
		success: function(users) {
			$("#user-container1").empty();
			
			console.log("length of users is "+users.length);
	
	  if (users.length === 0) {
		  console.log("inside of user length 0 for acceptedList");
        $("#user-container1").html(`
            <div class="no-users-message" style="text-align: center; padding: 20px; font-size: 18px; color: blue;">
                <h1>&#128101; Followers List is Empty.<h1>
            </div>
        `);
     
        return; // stop further processing
    }
    
			let userPromises = users.map((user, index) => {
				return new Promise(resolve => {
					fetchUserImage(user.id, function(imageData) {
						fetchAllRequests(function(allRequests) {
						let skillsList = user.skills && user.skills.length > 0 ? user.skills.join(", ") : "No skills added";
						let profileImage = imageData ? `data:image/jpeg;base64,${imageData}` : "default-profile.png";
						let currentUserId = Number(document.getElementById('currentUserId').value);

						let matchingRequest = allRequests.find(req =>
    (req.senderId === currentUserId && req.receiverId === user.id)||  (req.senderId === user.id && req.receiverId === currentUserId)
);


let requestStatus = matchingRequest?.status;
let requestId1 = matchingRequest?.id;


console.log("rquest status is "+requestId1);
						let buttonHtml = `
						<div class="button-container1" data-request-id="${requestId1}">
							<button class="send-request-btn5" onclick="openMessageModal(${user.id}, '${user.name}', '${profileImage}')">Message</button>
							<button id="requestButton1-${user.id}" class="reject-btn1" onclick="updateRequestStatusReject(${requestId1})">Unfollow</button>
						</div>`;
						
						
						let userCard = `
							<div class="user-card">
								${buttonHtml}
								<img src="${profileImage}" alt="Profile Picture" class="profile-pic" style="cursor: pointer;"
            onclick="redirectToUserProfileformynetwork(${user.id})">
								<div class="user-info">
									<h3>${user.name}</h3>
									<p>Skills: ${skillsList}</p>
									${user.city ? `<p>City: ${user.city}</p>` : ``}
								</div>
							</div>`;

						resolve({ index, userCard });
					});
				});
			});
			});

			Promise.all(userPromises).then(userCards => {
				userCards.forEach(userData => {
					$("#user-container1").append(userData.userCard);
				});
			});
		},
		error: function(error) {
			console.log("Error fetching users:", error);
		}
	});
}


//Fetch all users with skills image and other data
function FetchPendingUsers() {
	console.log("inside all users fetching");
	
		// Show loading message before AJAX starts
	$("#user-container4").html(`
    <div class="loading-message" style="
        text-align: center;
        padding: 20px;
        font-size: 18px;
        color: #007bff;
        animation: pulse 1.5s infinite;
    ">
      <h1>&#8987; Loading users, please wait...<h1>
    </div>

    <style>
        @keyframes pulse {
            0% { opacity: 1; }
            50% { opacity: 0.5; }
            100% { opacity: 1; }
        }
    </style>
`);


	$.ajax({
		url: "RejectedList",
		type: "GET",
		dataType: "json",
		success: function(users) {
			$("#user-container4").empty();
			
			console.log("length of users is "+users.length);
	
	  if (users.length === 0) {
		  console.log("inside of user length 0 for acceptedList");
        $("#user-container4").html(`
            <div class="no-users-message" style="text-align: center; padding: 20px; font-size: 18px; color: blue;">
                <h1>&#128101; No Pending Reqeusts.<h1>
            </div>
        `);
     
        return; // stop further processing
    }
    
			let userPromises = users.map((user, index) => {
				return new Promise(resolve => {
					fetchUserImage(user.id, function(imageData) {
						fetchAllRequests(function(allRequests) {
						let skillsList = user.skills && user.skills.length > 0 ? user.skills.join(", ") : "No skills added";
						let profileImage = imageData ? `data:image/jpeg;base64,${imageData}` : "default-profile.png";
						let currentUserId = Number(document.getElementById('currentUserId').value);

						let matchingRequest = allRequests.find(req =>
    (req.senderId === currentUserId && req.receiverId === user.id)
);


						let buttonHtml = `<div class="button-container1">
							<button class="send-request-btn5" disabled style="background-color: #d3d3d3;  cursor: not-allowed;">Pending</button>
						</div>`;
						
						
						let userCard = `
							<div class="user-card">
								${buttonHtml}
								<img src="${profileImage}" alt="Profile Picture" class="profile-pic"style="cursor: pointer;"
            onclick="redirectToUserProfileformynetwork(${user.id})">
								<div class="user-info">
									<h3>${user.name}</h3>
									<p>Skills: ${skillsList}</p>
									${user.city ? `<p>City: ${user.city}</p>` : ``}
								</div>
							</div>`;

						resolve({ index, userCard });
					});
				});
			});
			});

			Promise.all(userPromises).then(userCards => {
				userCards.forEach(userData => {
					$("#user-container4").append(userData.userCard);
				});
			});
		},
		error: function(error) {
			console.log("Error fetching users:", error);
		}
	});
}


//Fetch accepted users after clicking folloowers tab
function fetchAcceptedUsersformy() {
	
	console.log("inside of fetchAcceptedUsersformy");
    // First, make sure the DOM elements exist
    $('#followers1').off('click').on('click', function (e) {
		console.log("inside of followers1")
        e.preventDefault();
        
        // Hide all tab content and show followers section
        $('.tab-content').hide();
        $('#followers').show();

        // Fetch and display accepted users
        FetchAcceptedUsers();
    });

    $('#pending1').off('click').on('click', function (e) {
        e.preventDefault();
        
        // Hide all tab content and show pending section
        $('.tab-content').hide();
        $('#pending').show();
        
        FetchPendingUsers()
    });

    // Optional: load followers tab by default
    $('#followers1').click(); 
}


//fuciton to fetch the filtered users
function applyFilters() {
	
	console.log("insdie of applyFilters");
    let location = $("#location").val();
    let skills = $("#skills").val().split(",").map(skill => skill.trim());
    let experience = $("#experience").val();
    let currentUserId = Number(document.getElementById('currentUserId').value);
    
    console.log(location);
    console.log(skills);
    console.log(experience);

    $.ajax({
        url: "filterUsers",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({ location, skills, experience }),
        dataType: "json",
        success: function(users) {
            $("#user-container").empty();
            
            // ✅ Check if no users found
    if (users.length === 0) {
        $("#user-container").html(`
            <div class="no-users-message" style="text-align: center; padding: 20px; font-size: 18px; color: red;">
                <h1>&#128542; Sorry, no users found with the selected filters.<h1>
            </div>
        `);
        closeFilterModal();
        return; // stop further processing
    }
    

            let userPromises = users.map((user, index) => {
                return new Promise(resolve => {
                    fetchUserImage(user.id, function(imageData) {
                        fetchPendingRequests(function(pendingRequests) {
                            fetchAllRequests(function(allRequests) {
                                let skillsList = user.skills && user.skills.length > 0 ? user.skills.join(", ") : "No skills added";
                                let profileImage = imageData ? `data:image/jpeg;base64,${imageData}` : "default-profile.png";
								let currentUserId = Number(document.getElementById('currentUserId').value);

                                let requestStatus = allRequests.find(req =>
                                    (req.senderId === currentUserId && req.receiverId === user.id) ||
                                    (req.senderId === user.id && req.receiverId === currentUserId)
                                )?.status;

                                let isRequestReceived = pendingRequests.some(req => req.senderId === user.id);
                                let buttonHtml = "";

                                if (isRequestReceived) {
                                    let requestId = pendingRequests.find(req =>
                                        req.senderId === user.id && req.receiverId === currentUserId
                                    )?.id;
                                    buttonHtml = `
                                    <div class="button-container" data-request-id="${requestId}" 
                                                                  data-user-id="${user.id}" 
                                                                  data-LoggedInUser-id="${currentUserId}" 
                                                                  data-user-name="${user.name}" 
                                                                  data-user-image="${profileImage}">

                                        <button class="accept-btn" onclick="updateRequestStatus(${requestId}, 'Accepted')">Accept</button>
                                        <button class="reject-btn" onclick="updateRequestStatusReject(${requestId})">Reject</button>
                                    </div>`;
                                } else if (requestStatus === "Pending") {
                                    buttonHtml = `<button id="requestButton-${user.id}" class="send-request-btn" disabled style="background-color: #d3d3d3;  cursor: not-allowed;">Pending</button>`;
                                } else if (requestStatus === "Accepted") {
                                    buttonHtml = `<button class="send-request-btn" onclick="openMessageModal(${user.id}, '${user.name}', '${profileImage}')">Message</button>`;
                                } else {
                                    buttonHtml = `<button id="requestButton-${user.id}" class="send-request-btn" onclick="sendRequest(${user.id})">Send Request</button>`;
                                }
								
								
                                let userCard = `
                                <div class="user-card">
                                    ${buttonHtml}
                                    <img src="${profileImage}" alt="Profile Picture" class="profile-pic" style="cursor: pointer;"
            onclick="redirectToUserProfile(${user.id})">
                                    <div class="user-info">
                                        <h3>${user.name}</h3>
                                        <p>Skills: ${skillsList}</p>
                                        ${user.city ? `<p>City: ${user.city}</p>` : ``}
                                       
                                    </div>
                                </div>`;

                                resolve({ index, userCard });
                            });
                        });
                    });
                });
            });

            Promise.all(userPromises).then(userCards => {
              
                userCards.forEach(userData => {
                    $("#user-container").append(userData.userCard);
                });
            });
            
            closeFilterModal();
        },
        error: function(error) {
            console.log("Error fetching filtered users:", error);
        }
    });
}

//fuciton to fetch the  users by name
function SearchUsersByName() {
	
	console.log("insdie of applyFilters");
    let searchBox = $("#searchBox").val();
 
	console.log(searchBox);
    $.ajax({
        url: "SearchUsers",
        type: "GET",
        
        data: { searchBox: searchBox },
        dataType: "json",
        success: function(users) {
            $("#user-container").empty();
            
            // ✅ Check if no users found
    if (users.length === 0) {
        $("#user-container").html(`
            <div class="no-users-message" style="text-align: center; padding: 20px; font-size: 18px; color: red;">
                <h1>&#128542; Sorry, no users found with the selected filters.<h1>
            </div>
        `);
        closeFilterModal();
        return; // stop further processing
    }
    

            let userPromises = users.map((user, index) => {
                return new Promise(resolve => {
                    fetchUserImage(user.id, function(imageData) {
                        fetchPendingRequests(function(pendingRequests) {
                            fetchAllRequests(function(allRequests) {
                                let skillsList = user.skills && user.skills.length > 0 ? user.skills.join(", ") : "No skills added";
                                let profileImage = imageData ? `data:image/jpeg;base64,${imageData}` : "default-profile.png";
								let currentUserId = Number(document.getElementById('currentUserId').value);

                                let requestStatus = allRequests.find(req =>
                                    (req.senderId === currentUserId && req.receiverId === user.id) ||
                                    (req.senderId === user.id && req.receiverId === currentUserId)
                                )?.status;

                                let isRequestReceived = pendingRequests.some(req => req.senderId === user.id);
                                let buttonHtml = "";

                                if (isRequestReceived) {
                                    let requestId = pendingRequests.find(req =>
                                        req.senderId === user.id && req.receiverId === currentUserId
                                    )?.id;
                                    console.log("request id in Search users is"+requestId);
                                    buttonHtml = `
                                    <div class="button-container" data-request-id="${requestId}" 
                                                                  data-user-id="${user.id}" 
                                                                  data-LoggedInUser-id="${currentUserId}" 
                                                                  data-user-name="${user.name}" 
                                                                  data-user-image="${profileImage}">

                                        <button class="accept-btn" onclick="updateRequestStatus(${requestId}, 'Accepted')">Accept</button>
                                        <button class="reject-btn" onclick="updateRequestStatusReject(${requestId})">Reject</button>
                                    </div>`;
                                } else if (requestStatus === "Pending") {
                                    buttonHtml = `<button id="requestButton-${user.id}" class="send-request-btn" disabled style="background-color: #d3d3d3;  cursor: not-allowed;">Pending</button>`;
                                } else if (requestStatus === "Accepted") {
                                    buttonHtml = `<button class="send-request-btn" onclick="openMessageModal(${user.id}, '${user.name}', '${profileImage}')">Message</button>`;
                                } else {
                                    buttonHtml = `<button id="requestButton-${user.id}" class="send-request-btn" onclick="sendRequest(${user.id})">Send Request</button>`;
                                }
								
								
                                let userCard = `
                                <div class="user-card">
                                    ${buttonHtml}
                                    <img src="${profileImage}" alt="Profile Picture" class="profile-pic" style="cursor: pointer;"
            onclick="redirectToUserProfile(${user.id})">
                                    <div class="user-info">
                                        <h3>${user.name}</h3>
                                        <p>Skills: ${skillsList}</p>
                                        ${user.city ? `<p>City: ${user.city}</p>` : ``}
                                       
                                    </div>
                                </div>`;

                                resolve({ index, userCard });
                            });
                        });
                    });
                });
            });

            Promise.all(userPromises).then(userCards => {
              
                userCards.forEach(userData => {
                    $("#user-container").append(userData.userCard);
                });
            });
            
            closeFilterModal();
        },
        error: function(error) {
            console.log("Error fetching filtered users:", error);
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
	console.log("receiverId is"+receiverId);
	const button = document.getElementById(`requestButton-${receiverId}`);
console.log("Found button:", `requestButton-${receiverId}`, button ? button.outerHTML : "❌ Button not found");

	if (button) {
		// Immediately give feedback to user
		console.log("inside of button ");
		button.disabled = true;
		button.textContent = "Sending...";
	}

	$.ajax({
		type: "POST",
		url: `send/${receiverId}`,
		dataType: "json",
		success: function() { 
			console.log("Request sent successfully");
			if (button) {
				console.log("inside of pending button of ajax");
				button.textContent = "Pending";
				button.disabled = true;
				button.style.backgroundColor = "#d3d3d3"; // light grey

button.style.cursor = "not-allowed";
			}
		},
		error: function() {
			alert("Error sending request.");
			if (button) {
				button.textContent = "Send Request";
				button.disabled = false;
			}
		}
	});
}



// Function to send a connection request for accepted users
function sendRequestforAccepted(receiverId) {
		console.log("receiverId is"+receiverId);
	const button = document.getElementById(`requestButton1-${receiverId}`);
console.log("Found button:", `requestButton1-${receiverId}`, button ? button.outerHTML : "❌ Button not found");

	if (button) {
		// Immediately give feedback to user
		console.log("inside of button ");
		button.disabled = true;
		button.textContent = "Sending...";
	}

	$.ajax({
		type: "POST",
		url: `send/${receiverId}`,
		dataType: "json",
		success: function() { 
			console.log("Request sent successfully");
			if (button) {
				console.log("inside of pending button of ajax");
				button.textContent = "Pending";
				button.disabled = true;
				button.style.backgroundColor = "#d3d3d3"; // light grey

button.style.cursor = "not-allowed";
			}
		},
		error: function() {
			alert("Error sending request.");
			if (button) {
				button.textContent = "Send Request";
				button.disabled = false;
			}
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

				// Retrieve user details from data attributes
				let userId = buttonContainer1.getAttribute("data-user-id");
				let userName = buttonContainer1.getAttribute("data-user-name");
				let userImage = buttonContainer1.getAttribute("data-user-image");



				buttonContainer1.innerHTML = `<button class="send-request-btn" onclick="openMessageModal(${userId}, '${userName}', '${userImage}')">Message</button>`;

			}

		},
		error: function() {
			
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
			console.log("Requested UserId is " + userId);

			// Try to find both types of containers
			const buttonContainer = document.querySelector(`.button-container[data-request-id="${requestId}"]`);
			const buttonContainer1 = document.querySelector(`.button-container1[data-request-id="${requestId}"]`);

			const sendRequestBtnHTML = `<button id="requestButton-${userId}" class="send-request-btn12" onclick="sendRequest(${userId})">Send Request</button>`;
			const sendRequestBtnHTMLAccepted = `<button id="requestButton1-${userId}" class="send-request-btn12" onclick="sendRequestforAccepted(${userId})">Send Request</button>`;

			// Update for findyourpartner.jsp
			if (buttonContainer) {
				console.log("inside of buttonContainer");
				buttonContainer.innerHTML = sendRequestBtnHTML;
			}

			// Update for network.jsp
			if (buttonContainer1) {
				console.log("inside of buttonContainer1");
				buttonContainer1.innerHTML = sendRequestBtnHTMLAccepted;
			}
		},
		error: function(error) {
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



// Open the modal and set user details
// Open the modal and set user details
function openMessageModal(userId, userName, userImage) {
	
    document.getElementById("messageModal").setAttribute("data-receiver-id", userId);
    document.getElementById("messageUserName").innerText = userName;
    document.getElementById("messageUserImg").src = userImage;
    document.getElementById("messageModal").style.display = "block";
    
    setTimeout(() => {
        let chatBody = document.getElementById("chatMessages");
        if (chatBody) {
            chatBody.scrollTop = chatBody.scrollHeight;
            console.log("Scrolled to bottom!");
        } else {
            console.warn("chatMessages element not found!");
        }
    }, 2000);

    // When user sees message, update to SEEN
    document.getElementById("messageModal").addEventListener("click", function() {
        let messages = document.querySelectorAll(".received-message");
        console.log("messageModal is clicked");
        messages.forEach(msgElement => {
            console.log("inside messageModal if there are received messages");
            let messageId = msgElement.getAttribute("data-message-id");
            if (messageId) {
                console.log("message id is " + messageId);
                updateMessageStatus(messageId, "SEEN");
            }
        });
    });

    let currentUserId = Number(document.getElementById('currentUserId').value);
    let senderId = currentUserId;  // Get the logged-in user's ID
    let receiverId = document.getElementById("messageModal").getAttribute("data-receiver-id");

    // Fetch previous chat messages
fetchChatMessages(senderId, receiverId).then(messages => {
    console.log("Checking messages for status update...");

    let loggedInUserId = Number(document.getElementById('currentUserId').value);

    // ✅ Filter only received messages (messages sent by the other user)
    let receivedMessages = messages.filter(msg => msg.receiverId == loggedInUserId && msg.senderId==userId);

    receivedMessages.forEach(msg => {
        console.log("Received Message ID:", msg.id);

        if (msg.status === "DELIVERED") {  
            console.log("Marking message as SEEN:", msg.id);
            updateMessageStatus(msg.id, "SEEN");
        }
    
    });

}).catch(error => {
    console.error("Error fetching messages:", error);
});


    // Send button functionality
    $(document).off("click", "#sendBtn0").on("click", "#sendBtn0", function() {
        console.log("inside sendBtn0 event listener");

        let currentUserId = Number(document.getElementById('currentUserId').value);
        let senderId = currentUserId;  // Get the logged-in user's ID
        let receiverId = document.getElementById("messageModal").getAttribute("data-receiver-id");
        let messageText = document.getElementById("messageInput").value;
        let fileInput = document.getElementById("fileInput").files[0];  // File input if needed

        // Check if at least a message or a file is provided
        if (messageText.trim() === "" && !fileInput) {
            alert("Please enter a message or select a file!");
            return;
        }

        // Call the function to send message via WebSocket
        sendMessage(senderId, receiverId, messageText, fileInput);

        // Clear the input field after sending the message
        document.getElementById("messageInput").value = "";
        document.getElementById("fileInput").value = "";
    });
}

// Close the modal
function closeMessageModal() {
	document.getElementById("messageModal").style.display = "none";
}

//code to retrieve the old messages
function fetchChatMessages(senderId, receiverId) {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: "GET",
            url: "chat",
            data: { senderId: senderId, receiverId: receiverId },
            success: function(messages) {
                let loggedInUserId = Number(document.getElementById('currentUserId').value);
                console.log("inside of fetchChatMessages");
                let chatBody = document.getElementById("chatMessages");
                chatBody.innerHTML = ""; // Clear existing messages

                messages.forEach(msg => {
                    let messageClass = (msg.senderId === loggedInUserId) ? "sent-message" : "received-message";
                    let messageElement = `<div class="${messageClass}"><p>${msg.messageText || ''}</p>`;

                    // Display message status (Sent, Delivered, Seen)
                    if (msg.senderId === loggedInUserId && msg.status) {
                        let statusText = '';
                        switch (msg.status) {
                            case 'SENT':
                                statusText = 'Sent';
                                break;
                            case 'DELIVERED':
                                statusText = 'Delivered';
                                break;
                            case 'SEEN':
                                statusText = 'Seen';
                                break;
                            default:
                                statusText = '';
                        }
                        messageElement += `<small class="status">${statusText}</small>`; // Display status below the message
                    }

                    // Display file attachment
                    if (msg.filePath) {
                        messageElement += `<p><a href="/messages/download/${msg.filePath}" download>📂 Download File</a></p>`;
                    }

                    messageElement += `</div>`;
                    chatBody.innerHTML += messageElement;
                });

                resolve(messages);  // Resolve the promise after messages are successfully fetched
            },
            error: function(error) {
                console.error("Error fetching messages:", error);
                reject(error);  // Reject the promise if there is an error
            }
        });
    });
}




//code to handle the realtime chatting

let socket = new SockJS('/build_your_team1/websocket');
let stompClient = Stomp.over(socket);

function connect() {
	
	console.log("inside connect function");
	stompClient.connect({}, function(frame) {
		
			let loggedInUserId = Number(document.getElementById('currentUserId').value);

		
		console.log("inside stomclient Connect methods");
		console.log('Connected: ' + frame);
		stompClient.subscribe(`/topic/messages/${loggedInUserId}`, function(messageOutput) {
			
			console.log("inside of recived message succesfully");
			let newMessage = JSON.parse(messageOutput.body);
			displayNewMessage(newMessage);
			
			    // ✅ Mark message as DELIVERED when received
    updateMessageStatus(newMessage.id, "DELIVERED");
		});
		
		 // Subscribe to message status updates
        stompClient.subscribe('/topic/messages/status/' + loggedInUserId, function(statusUpdate) {
			console.log("inside stomclien.subscribe for message status");
            let updatedMessage = JSON.parse(statusUpdate.body);
            updateMessageUIStatus(updatedMessage);
        });
        
	});
}

function displayNewMessage(msg) {
	
				let loggedInUserId = Number(document.getElementById('currentUserId').value);

	let chatBody = document.getElementById("chatMessages");
	let messageClass = (msg.senderId === loggedInUserId) ? "sent-message" : "received-message";
	let messageElement = `<div class="${messageClass}"  data-message-id="${msg.id}"><p>${msg.messageText || ''}</p>`;

	if (msg.senderId === loggedInUserId && msg.status) {
		let statusText = msg.status === 'SENT' ? 'Sent' :
			msg.status === 'DELIVERED' ? 'Delivered' :
				msg.status === 'SEEN' ? 'Seen' : '';
		messageElement += `<small class="status">${statusText}</small>`;
	}

	if (msg.filePath) {
		messageElement += `<p><a href="/messages/download/${msg.filePath}" download>📂 Download File</a></p>`;
	}

	messageElement += `</div>`;
	chatBody.innerHTML += messageElement;
	chatBody.scrollTop = chatBody.scrollHeight; // Auto-scroll to new messages
}

connect();

//function to send the message

function sendMessage(senderId, receiverId, messageText, file) {
	if (file) {
		
		console.log("inside if file is present");
		// First, upload the file using REST API
		let formData = new FormData();
		formData.append("file", file);

		fetch("/uploadFile", {
			method: "POST",
			body: formData
		})
			.then(response => response.json())
			.then(data => {
				let filePath = data.filePath; // Get file URL/path from response

				// Now send the message via WebSocket with the file path
				let messageData = {
					senderId: senderId,
					receiverId: receiverId,
					messageText: messageText,
					filePath: filePath
				};
				stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(messageData));
			})
			.catch(error => console.error("File upload failed", error));
	} else {
		console.log("inside if file is not present");
		// Send only text message via WebSocket
		let messageData = {
			senderId: senderId,
			receiverId: receiverId,
			messageText: messageText,
			filePath: null
		};
		stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(messageData));
	}
}



//update the message ui status

function updateMessageUIStatus(updatedMessage) {
	
	console.log("inside updateMessageUIStatus");
    let messageElement = document.querySelector(`[data-message-id="${updatedMessage.id}"]`);
    if (messageElement) {
        let statusElement = messageElement.querySelector(".status");
        if (statusElement) {
            statusElement.textContent = updatedMessage.status; // Update status (Sent → Delivered → Seen)
        }
    }
}


//fucntion to send an HTTP request to the backend

function updateMessageStatus(messageId, status) {
    $.ajax({
        url: "updateStatus",  // The full URL path for the request
        type: "POST",  // Use POST request
        contentType: "application/json",
        dataType: "json",  // Set content type as JSON
        data: JSON.stringify({
            messageId: messageId,
            status: status
        }),  // Send the message ID and status in the request body
        success: function(updatedMessage) {
			
			console.log("inside ajax of update messageStatus");
/* updateMessageUIStatus(updatedMessage); */ // ✅ Update UI in real-time       
  },
        error: function(xhr, status, error) {
            console.error("Error updating status:", error);  // If error occurs, log it
        }
    });
}

//script for adding blog in database 

function CreatPost(){
$("#createBlogBtn").off("click").on("click", function () {
    var formData = new FormData();
    formData.append("title", $("input[name='title']").val());
    formData.append("description", $("textarea[name='description']").val());
    formData.append("image", $("input[name='image']")[0].files[0]);
    
    console.log("enterd inside createpost");

    $.ajax({
        type: "POST",
        url: "addBlogss",
        data: formData,
        datatype:"json",
        contentType: false,
        processData: false,
        success: function (response) {
            console.log("Blog added:", response);
            alert("Blog posted successfully!");
                            location.reload(); // 👈 Reload page after alert

            $("input[name='title']").val("");
            $("textarea[name='description']").val("");
            $("input[name='image']").val("");
            
        },
        error: function () {
            alert("Failed to add blog.");
        }
    });
});
}



 