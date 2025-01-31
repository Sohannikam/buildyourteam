<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<%@page isELIgnored="false"%>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SoulScroll - Signup</title>

<!-- bootstrap css cdn -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">

<!-- font awesome cdn -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
	integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />

<!-- external css  -->
<link href="<c:url value='/Resource/css/login.css' />" rel="stylesheet">

<script src="https://apis.google.com/js/platform.js" async defer></script>

<script>
	function validatePassword() {
		var password = document.getElementById("signup-password").value;
		var confirmPassword = document
				.getElementById("signup-confirm-password").value;
		var phone = document.getElementById("phone_no").value;

		if (password !== confirmPassword) {
			alert("Password and Confirm Password do not match!");
			return false;
		}

		if (phone.length !== 10) {
			alert("Invalid Phone Number!");
			return false;
		}
		return true;
	}
</script>



</head>
<body>

	<div
		class="container-fluid d-flex justify-content-center align-items-center">
		<div
			class="container  d-flex flex-column justify-content-center align-items-center">
			<div class="container inner-container">
				<div class="row d-flex justify-content-center ">
					<div class="col-md-6">


						<!-- Display email exists message -->
						<c:if test="${not empty message}">
							<script>
								window.onload = function() {
									alert("${message}");
								};
							</script>
						</c:if>
						
						<!-- Display phone exists message -->
						<c:if test="${not empty newmessage}">
							<script>
								window.onload = function() {
									alert("${newmessage}");
								};
							</script>
						</c:if>
						
						
						<!-- signup form here  -->
						<p class="fs-1 fw-bold text-center my-3 login-txt">Sign Up</p>
						<div class="container mt-4 d-flex justify-content-center">

							<form method="Post" action="register" id="signup-form"
								onsubmit="return validatePassword();">
								<div class="form-group mb-4">
									<i class="fa-solid fa-user"></i> <input
										class="input text-center" placeholder="Enter your name"
										type="text" name="name" id="signup-name"  value="${user.name != null ? user.name : ''}" required="required">
								</div>
								<div class="form-group mb-4">
									<i class="fa-solid fa-envelope"></i> <input
										class="input text-center" placeholder="Enter your email"
										type="email" name="email" id="signup-email" value="${user.email != null ? user.email : ''}"
										required="required">
								</div>

								<div class="form-group mb-4">
									<i class="fa-solid fa-lock"></i> <input
										class="input text-center" placeholder="Enter your password"
										type="password" name="pass" id="signup-password" value="${user.pass != null ? user.pass : ''}"
										required="required">
								</div>
								<div class="form-group mb-4">
									<i class="fa-solid fa-key"></i> <input
										class="input text-center" placeholder="Confirm password"
										type="password" name="cpass" id="signup-confirm-password" value="${user.cpass != null ? user.cpass : ''}"
										required="required">
								</div>

								<div class="form-group mb-4">
									<i class="fa-solid fa-phone"></i><input
										class="input text-center" placeholder="Enter Mobile No"
										type="tel" name="phone" id="phone_no"  value="${user.phone != null ? user.phone : ''}" required="required">
								</div>

								<div class="form-group mb-4 d-flex align-items-center">
									<i class="fa-solid fa-venus-mars me-3"></i> <select
										class="form-control input text-center" name="gen" id="gender"
										required="required">
										<option value="" disabled selected>Select Gender</option>
										<option value="male">Male</option>
										<option value="female">Female</option>
										<option value="other">Other</option>
									</select>
								</div>


								<div class="form-check mb-4">
									<input class="form-check-input" type="checkbox" value=""
										id="signup-flexCheckDefault"> <label
										class="form-check-label ms-2" for="signup-flexCheckDefault">
										I agree to all statements in the Terms of Service </label>
								</div>
								<button type="submit" class="btn btn-primary mb-4">Sign
									up</button>

								<div class="mb-5">
									<a href="${pageContext.request.contextPath}/login"
										class="btn btn-outline-danger">I am already a member</a>

								</div>
							</form>
						</div>
					</div>
					<div class="col-md-4 text-center">
						<div class="text-center mb-4">
							<img
								src="<c:url value="/Resource/images/Innovative_Team_Builder2.jpg"/>"
								alt="Centered Image" class="mx-auto mt-5 img-fluid">
						</div>

						<div class="container  mt-3">
							<p>
								Or Sign Up with : <span class="ms-3"><i
									class="fa-brands fa-facebook-f fa-lg me-3 icons "></i><i
									class="fa-brands fa-x-twitter fa-lg me-3 icons "></i><i
									class="fa-brands fa-google fa-lg icons "></i></span>
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>




	<!-- bootstrap js cdn -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
		crossorigin="anonymous"></script>






</body>
</html>
