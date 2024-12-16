<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7fc;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 60%;
            margin: 0 auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #333;
        }
        form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }
        label {
            font-size: 16px;
            font-weight: bold;
            color: #333;
        }
        input[type="text"], input[type="number"], textarea {
            padding: 10px;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 5px;
            width: 100%;
            box-sizing: border-box;
        }
        textarea {
            height: 100px;
            resize: vertical;
        }
        button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 12px;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
        }
        button:hover {
            background-color: #0056b3;
        }
        .message {
            margin-top: 20px;
            text-align: center;
            font-size: 16px;
            color: #28a745;
        }
        .message.error {
            color: #dc3545;
        }
        .message.info {
            color: #17a2b8;
        }
        .back-button {
            margin-top: 20px;
            text-align: center;
        }
        .back-button button {
            background-color: #f44336;
            color: white;
            border: none;
            padding: 12px 20px;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
        }
        .back-button button:hover {
            background-color: #d32f2f;
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
    <div class="container">
        <h2>Add New Product</h2>
        <form action="ProductController" method="post">
            <input type="hidden" name="action" value="addProduct">
            
            <label for="name">Product Name:</label>
            <input type="text" id="name" name="name" required placeholder="Enter product name">
            
            <label for="description">Description:</label>
            <textarea id="description" name="description" required placeholder="Enter product description"></textarea>
            
            <label for="category">Category:</label>
            <input type="text" id="category" name="category" required placeholder="Enter product category">
            
            <label for="price">Price ($):</label>
            <input type="number" id="price" name="price" step="0.01" required placeholder="Enter product price">
            
            <label for="imagePath">Image URL:</label>
            <input type="text" id="imagePath" name="imagePath" required placeholder="Enter image URL">
            
            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" name="quantity" required placeholder="Enter product quantity">
            
            <button type="submit">Add Product</button>
        </form>

        <div class="message">
            <p>${message}</p>
        </div>

        <div class="back-button">
            <form action="admin.jsp" method="get">
                <button type="submit">Back to Admin Page</button>
            </form>
        </div>
    </div>
    <div class="footer">
        <p>&copy; ChippyChips. All rights reserved.</p>
    </div>
</body>
</html>

