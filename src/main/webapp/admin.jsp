<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f7fc;
            color: #333;
        }
        header {
            background-color: #007bff;
            color: white;
            padding: 20px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        header h1 {
            margin: 0;
            font-size: 28px;
        }
        .home-button {
            background-color: #28a745;
            color: white;
            padding: 10px 20px;
            font-size: 14px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            box-shadow: 0 3px 5px rgba(0, 0, 0, 0.1);
            transition: background-color 0.3s, transform 0.3s;
        }
        .home-button:hover {
            background-color: #218838;
            transform: scale(1.05);
        }
        .container {
            width: 80%;
            margin: 30px auto;
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 20px;
        }
        .button-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 20px;
            margin: 20px 0;
        }
        .button-container a, .button-container form button {
            background-color: #007bff;
            color: white;
            padding: 15px 25px;
            font-size: 16px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            text-decoration: none;
            text-align: center;
            box-shadow: 0 3px 5px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s, background-color 0.3s;
        }
        .button-container a:hover, .button-container form button:hover {
            background-color: #0056b3;
            transform: scale(1.05);
        }
        .button-container form {
            display: inline;
        }
        footer {
            margin-top: 50px;
            text-align: center;
            font-size: 14px;
            color: #666;
            padding: 20px 0;
            background-color: #f8f9fa;
            box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
    <header>
        <h1>Admin Dashboard</h1>
        <a href="ProductController?action=catalog" class="home-button">Back to Home</a>
    </header>
    <div class="container">
        <div class="button-container">
            <a href="addProduct.jsp">Add New Product</a>
            <a href="AdminProductController">Edit Product</a>
            <form action="AdminOrderController" method="get">
                <button type="submit">View Sales History</button>
            </form>
            <a href="AdminUserController">Manage Users</a>
        </div>
    </div>
    <footer>
        <p>&copy; ChippyChips. All rights reserved.</p>
    </footer>
</body>
</html>
