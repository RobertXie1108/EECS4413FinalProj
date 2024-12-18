<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Product</title>
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
            width: 100%;
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
        <h1>Edit Product</h1>
        <form action="AdminProductController" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="${product.id}">
            
            <label>Product Name:</label>
            <input type="text" name="name" value="${product.name}" required>
            
            <label>Description:</label>
            <input type="text" name="description" value="${product.description}" required>
            
            <label>Category:</label>
            <input type="text" name="category" value="${product.category}" required>
            
            <label>Price:</label>
            <input type="number" step="0.01" name="price" value="${product.price}" required>
            
            <label>Image Path:</label>
            <input type="text" name="imagePath" value="${product.imagePath}" required>
            
            <label>Quantity:</label>
            <input type="number" name="quantity" value="${product.quantity}" required>
            
            <button type="submit">Update Product</button>
        </form>
    </div>
</body>
</html>
