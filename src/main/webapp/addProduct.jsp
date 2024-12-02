<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Product</title>
</head>
<body>
    <h2>Add New Product</h2>
    <form action="ProductController" method="post">
        <input type="hidden" name="action" value="addProduct">
        <label>Product Name:</label>
        <input type="text" name="name" required><br>
        <label>Description:</label>
        <textarea name="description" required></textarea><br>
        <label>Category:</label>
        <input type="text" name="category" required><br>
        <label>Price:</label>
        <input type="number" step="0.01" name="price" required><br>
        <label>Image URL:</label>
        <input type="text" name="imageUrl" required><br>
        <label>Quantity:</label>
        <input type="number" name="quantity" required><br>
        <button type="submit">Add Product</button>
    </form>
    <p>${message}</p>
</body>
</html>