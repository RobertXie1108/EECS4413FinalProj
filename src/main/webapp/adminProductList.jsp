<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin - Update Products</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
        }
        header {
            display: flex;
            justify-content: space-between;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
        }
        h1 {
            text-align: center;
            color: #007bff;
        }
        .product-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            margin: 20px;
        }
        .product {
            display: inline-block;
            width: 200px;
            margin: 10px;
            border: 1px solid #ccc;
            text-align: center;
            background-color: white;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            border-radius: 5px;
            cursor: pointer;
            transition: transform 0.2s;
        }
        .product:hover {
            transform: scale(1.05);
        }
        .product img {
            width: 100px;
            height: 100px;
            margin: 10px;
            object-fit: cover;
            border-radius: 5px;
        }
        .product h3 {
            margin: 10px 0;
            font-size: 16px;
        }
        a {
            text-decoration: none;
            color: #333;
        }
        .delete-button {
        background-color: #dc3545;
        color: white;
        border: none;
        padding: 5px 10px;
        border-radius: 5px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    .delete-button:hover {
        background-color: #c82333;
    }

    .edit-button {
        background-color: #007bff;
        color: white;
        border: none;
        padding: 5px 10px;
        border-radius: 5px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    .edit-button:hover {
        background-color: #0056b3;
    }
    </style>
</head>
<body>
    <header>
        <h2>Admin - Update Products</h2>
        <form action="admin.jsp" method="get">
            <button type="submit" style="background-color:#ffc107; border:none; color:#333; padding:10px; border-radius:5px; cursor:pointer;">
                Back to Dashboard
            </button>
        </form>
    </header>
    <h1>Select a Product to Edit</h1>
    <div class="product-container">
    <c:forEach var="product" items="${products}">
        <div class="product">
            <img src="${product.imagePath}" alt="${product.name}">
            <h3>${product.name}</h3>
            <p>Price: $${product.price}</p>
            <form action="AdminProductController" method="post" style="display:inline;">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="id" value="${product.id}">
                <button type="submit" class="delete-button">Delete</button>
            </form>
            <form action="AdminProductController" method="get" style="display:inline;">
                <input type="hidden" name="action" value="edit">
                <input type="hidden" name="id" value="${product.id}">
                <button type="submit" class="edit-button">Edit</button>
            </form>
        </div>
    </c:forEach>
</div>

</body>
</html>
