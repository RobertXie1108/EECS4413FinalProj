<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit User</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
        }
        .form-container {
            max-width: 600px;
            margin: 30px auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], input[type="number"] {
            width: 97%;
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        button {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 10px 15px;
            cursor: pointer;
            border-radius: 5px;
        }
        button:hover {
            background-color: #218838;
        }
        
    </style>
</head>
<body>
	<div class="form-container">
		<h1>Edit User Information</h1>
		<form action="AdminUserController" method="post">
			<input type="hidden" name="id" value="${user.id}">
			<input type="hidden" name="username" value="${user.username}">
			<input type="hidden" name="password" value="${user.password}">
			
			<label>User ID:</label>
			<p>${user.id}</p>
			
			<label>Username:</label>
			<p>${user.username}</p>
			
			<label>Password:</label>
			<p>${user.password}</p>
			
			<label>Name:</label>
			<input type="text" name="fullName" value="${user.fullName}" required>
			
			<label>Shipping Address:</label>
			<input type="text" name="shippingAddress" value="${user.shippingAddress}" required>
			
			<label>Card Number:</label>
			<input type="text" name="cardNumber" value="${user.creditCardNumber}" required>
			
			<label>Card Expiry:</label>
			<input type="text" name="cardExpiry" value="${user.creditCardExpiry}" required>
			
			<label>Card CVV</label>
			<input type="text" name="cardCVV" value="${user.creditCardCVV}" required>
			
			<button type="submit">Update User</button>
		</form>
	</div>
</body>
</html>