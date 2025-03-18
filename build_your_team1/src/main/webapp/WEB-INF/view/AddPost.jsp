<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<%@page isELIgnored="false"%>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>

<%--  <link href="<c:url value='/Resource/css/style.css' />" rel="stylesheet"> --%>
<link href="<c:url value='/Resource/css/style1.css' />" rel="stylesheet">
<link href="<c:url value='/Resource/css/style3.css' />" rel="stylesheet">

<style>
body {
	
}

.container5 {
	max-width: 700px;
	background: white;
	padding: 30px;
	border-radius: 10px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	width: 100%;
	margin-top: 90px;
}

.form-label {
	font-weight: bold;
}

textarea {
	resize: none;
}

.container4 {
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
}
</style>
</head>
<body>

	<div class="container4">

		<div class="container5">
			<h2 class="text-center mb-4">Create a New Blog</h2>

			<form action="CreateBlogServlet1" method="post">
				<!-- Title -->
				<div class="mb-3">
					<label for="title" class="form-label">Title:</label> <input
						type="text" id="title" name="title" class="form-control" required>
				</div>

				<!-- Description -->
				<div class="mb-3">
					<label for="description" class="form-label">Description:</label>
					<textarea id="description" name="description" rows="6"
						class="form-control" required></textarea>
				</div>

				<!-- Category -->
				<div class="mb-3">
					<label for="category" class="form-label">Category:</label> <select
						id="category" name="category" class="form-select" required>
						<option value="Technology">Technology</option>
						<option value="Sport">Sport</option>
						<option value="Travel">Travel</option>
						<option value="Film">Film</option>
					</select>
				</div><br>

				<label for="image"><b>Choose Image:</b></label> 
				<input type="file"
					id="image" name="image" accept="image/*" required="required"><br>

				<!-- Add other form elements or submit button if needed -->
				<br>

				<!-- Submit Button -->
				<div class="text-center">
					<button type="submit" class="btn btn-primary w-100">Create
						Blog</button>
				</div>
			</form>
		</div>

	</div>


	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>