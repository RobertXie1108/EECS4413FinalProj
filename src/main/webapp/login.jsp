<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7fc;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background-color: #ffffff;
            border-radius: 8px;
            padding: 40px;
            width: 100%;
            max-width: 400px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
            font-size: 24px;
            color: #333;
        }
        label {
            font-size: 14px;
            color: #555;
            margin-bottom: 6px;
            display: block;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
        }
        .button {
            width: 100%;
            padding: 12px;
            background-color: #4CAF50;
            border: none;
            color: white;
            font-size: 16px;
            cursor: pointer;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .button:hover {
            background-color: #45a049;
        }
        #backbutton {
        	display: block; 
        	text-align: center;
  			margin: 0 auto;
        	text-decoration: none;
        	color: #4CAF50
        }
        #backbutton:hover {
        	color: #45a049;
        }
        .error-message {
            color: red;
            text-align: center;
            font-size: 14px;
        }
        .footer {
            text-align: center;
            margin-top: 20px;
        }
        .footer a {
            color: #4CAF50;
            text-decoration: none;
        }
        .footer a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>Login</h2>
        <form action="login" method="post">
            <label for="username">Username:</label>
            <input type="text" name="username" required placeholder="Enter your username">
            
            <label for="password">Password:</label>
            <input type="password" name="password" required placeholder="Enter your password">
            
            <button class="button" type="submit">Login</button><br/><br/>
            <a href="ProductController" id="backbutton">Back to products</a>
        </form>

        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>

        <div class="footer">
            <p>Don't have an account? <a href="signup.jsp">Sign up here</a></p>
        </div>
    </div>

</body>
</html>