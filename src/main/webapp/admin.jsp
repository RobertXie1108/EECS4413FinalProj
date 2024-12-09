<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
            color: #333;
        }
        h2 {
            text-align: center;
            margin-top: 20px;
            color: #444;
        }
        .nav-container {
            display: flex;
            justify-content: center;
            margin: 30px 0;
        }
        .nav-container a {
            text-decoration: none;
            color: white;
            background-color: #007bff;
            padding: 10px 20px;
            margin: 0 10px;
            border-radius: 5px;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }
        .nav-container a:hover {
            background-color: #0056b3;
        }
        .footer {
            text-align: center;
            margin-top: 50px;
            font-size: 14px;
            color: #666;
        }
    </style>
</head>
<body>
    <h2>Admin Dashboard</h2>
    <div class="nav-container">
        <a href="addProduct.jsp">Add New Product</a>
        <a href="ProductController?action=catalog">View All Products</a>
        <a href="salesHistory.jsp">View Sales History</a>
        <a href="manageUsers.jsp">Manage Users</a>
    </div>
    <div class="footer">
        <p>&copy; ChippyChips. All rights reserved.</p>
    </div>
</body>
</html>
