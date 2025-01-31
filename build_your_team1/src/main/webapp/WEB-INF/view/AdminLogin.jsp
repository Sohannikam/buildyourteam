<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<%@page isELIgnored="false" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <style>
        body {
            background-color: #f4f4f4; /* Set a light background color */
            font-family: 'Arial', sans-serif; /* Set a font family */
            text-align: center; /* Center align the content */
            margin: 0;
            padding: 0;
        }

        h2 {
            color: #333; /* Set text color */
        }

        form {
            background-color: #fff; /* Set form background color */
            max-width: 300px;
            margin: 20px auto;
            padding: 20px;
            border-radius: 8px; /* Add rounded corners to the form */
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Add a subtle box shadow */
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #333; /* Set label text color */
        }

        input {
            width: 100%;
            padding: 8px;
            margin-bottom: 16px;
            box-sizing: border-box;
            border: 1px solid #ccc; /* Set input border color */
            border-radius: 4px; /* Add rounded corners to the input */
        }

        input[type="submit"] {
            background-color: #007bff; /* Set submit button background color */
            color: #fff; /* Set submit button text color */
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #0056b3; /* Change background color on hover */
        }
    </style>
</head>
<body>
    <h2>Admin Login</h2>
    <form action="LoginServlet" method="post">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br>
        
        <div style="display: flex; justify-content: space-between;">
        <input type="submit" value="Login" style="width: 130px; background-color: #007bff; color: #fff; border: none; padding: 8px 16px; border-radius: 4px; cursor: pointer;">
        <a href="${pageContext.request.contextPath}/login" style="text-decoration: none;">
            <input type="button" value="Back to Login" style="background-color: #dc3545; color: #fff; border: none; padding: 8px 16px; border-radius: 4px; cursor: pointer;">
        </a>
    </div>
        
    </form>
</body>
</html>
