<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <title>Product Home Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .product {
            display: inline-block;
            width: 200px;
            margin: 10px;
            border: 1px solid #ccc;
            padding: 10px;
            text-align: center;
        }
        .product img {
            width: 100px;
            height: 100px;
            object-fit: cover;
        }
        .button-container {
            display: flex;
            justify-content: space-around;
            margin: 20px 0;
        }
        .button-container form button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            font-size: 16px;
        }
        .button-container form button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <h1>Product Catalog</h1>
    <div class="button-container">
        <form action="admin.jsp" method="get">
            <button type="submit">Go to Admin Page</button>
        </form>
        <form action="login.jsp" method="get">
            <button type="submit">Go to User Profile</button>
        </form>
    </div>
    <div>
        <c:forEach var="product" items="${products}">
            <div class="product">
                <img src="${product.imagePath}" alt="${product.name}">
                <h3>${product.name}</h3>
                <p>Price: $${product.price}</p>
                <p>Category: ${product.category}</p>
                <form action="cart" method="post">
                    <input type="hidden" name="product_id" value="${product.id}">
                    <label for="quantity_${product.id}">Quantity:</label>
                    <input type="number" id="quantity_${product.id}" name="quantity" value="1" min="1">
                    <button type="submit" name="action" value="add">Add to Cart</button>
                </form>
                <a href="catalog?action=details&id=${product.id}">View Details</a>
            </div>
        </c:forEach>
    </div>
</body>
</html>
