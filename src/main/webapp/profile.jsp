<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Profile</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            color: #333;
            padding: 20px;
        }
        .form-container {
            max-width: 600px;
            margin: 50px auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
        }
        .form-group {
        	max-width: 570px;
            margin-bottom: 15px;
        }
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        button {
            display: block;
            width: 100%;
            background-color: #007bff;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background-color: #0056b3;
        }
        .message {
            text-align: center;
            font-size: 14px;
            color: #28a745;
        }
        .error-message {
            color: #dc3545;
        }
        .edit-button {
            background-color: #ffc107;
            width: auto;
            padding: 5px 10px;
            margin-top: 10px;
            border-radius: 5px;
            cursor: pointer;
        }
        .edit-button:hover {
            background-color: #e0a800;
        }
        .back-button {
            background-color: #28a745;
            width: auto;
            padding: 5px 10px;
            margin-top: 10px;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
        }
        .back-button:hover {
            background-color: #218838;
        }
        .logout-button {
            background-color: #dc3545;
            width: auto;
            padding: 5px 10px;
            margin-top: 10px;
            border-radius: 5px;
            cursor: pointer;
        }
        .logout-button:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h1>User Profile</h1>
        
        <c:if test="${not empty successMessage}">
            <p class="message">${successMessage}</p>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <p class="message error-message">${errorMessage}</p>
        </c:if>

        <form action="ProfileServlet" method="post">

            <div class="form-group">
                <label for="fullName">Full Name:</label>
                <input type="text" id="fullName" name="fullName" value="${user.fullName}" readonly>
            </div>

            <div class="form-group">
                <label for="shippingAddress">Shipping Address:</label>
                <input type="text" id="shippingAddress" name="shippingAddress" value="${user.shippingAddress}" readonly>
            </div>

            <div class="form-group">
                <label for="creditCardNumber">Credit Card Number:</label>
                <input type="text" id="creditCardNumber" name="creditCardNumber" value="${user.creditCardNumber}" readonly>
            </div>

            <div class="form-group">
                <label for="creditCardExpiry">Credit Card Expiry (MM/YY):</label>
                <input type="text" id="creditCardExpiry" name="creditCardExpiry" value="${user.creditCardExpiry}" readonly>
            </div>

            <div class="form-group">
                <label for="creditCardCVV">Credit Card CVV:</label>
                <input type="text" id="creditCardCVV" name="creditCardCVV" value="${user.creditCardCVV}" readonly>
            </div>

            <button type="submit" class="edit-button" id="editButton" onclick="enableEdit(); return false;">Edit Profile</button>
            <button type="submit" id="updateButton" style="display: none;">Update Profile</button>
        </form>

        <form action="/EECS4413FinalProject/ProductController?action=catalog" method="get">
            <button type="submit" class="back-button">Back to Home</button>
        </form>

        <form action="LogoutServlet" method="get">
            <button type="submit" class="logout-button">Logout</button>
        </form>
    </div>

    <script>
        function enableEdit() {
            // Make all input fields editable
            document.getElementById('fullName').removeAttribute('readonly');
            document.getElementById('shippingAddress').removeAttribute('readonly');
            document.getElementById('creditCardNumber').removeAttribute('readonly');
            document.getElementById('creditCardExpiry').removeAttribute('readonly');
            document.getElementById('creditCardCVV').removeAttribute('readonly');
            document.getElementById('updateButton').style.display = 'inline-block';
            document.getElementById('editButton').style.display = 'none';
        }
    </script>
</body>
</html>





