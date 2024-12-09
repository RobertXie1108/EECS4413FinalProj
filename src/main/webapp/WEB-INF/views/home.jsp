<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product Home Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
            color: #333;
        }
        header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
        }
        header .button-container {
            display: flex;
            align-items: center;
        }
        header .button-container button {
            margin-left: 10px;
            background-color: #ffc107;
            border: none;
            color: #333;
            padding: 5px 10px;
            font-size: 14px;
            border-radius: 5px;
            cursor: pointer;
        }
        header .button-container button:hover {
            background-color: #e0a800;
        }
        header .cart-button {
            background-color: #28a745;
            color: white;
            padding: 5px 10px;
            font-size: 14px;
            border-radius: 5px;
            border: none;
            cursor: pointer;
        }
        header .cart-button:hover {
            background-color: #218838;
        }
        h1 {
            text-align: center;
            margin-top: 20px;
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
            padding: 10px;
            text-align: center;
            background-color: white;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        .product img {
            width: 100px;
            height: 100px;
            object-fit: cover;
            border-radius: 5px;
        }
        .product button {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 5px 10px;
            font-size: 14px;
            border-radius: 5px;
            cursor: pointer;
        }
        .product button:hover {
            background-color: #218838;
        }
        .product a {
            display: inline-block;
            margin-top: 10px;
            text-decoration: none;
            color: #007bff;
            font-size: 14px;
        }
        .product a:hover {
            text-decoration: underline;
        }
        .admin-container {
            position: fixed;
            right: 20px;
            bottom: 20px;
        }
        .admin-container button {
            background-color: #ffc107;
            border: none;
            color: #333;
            padding: 5px 10px;
            font-size: 14px;
            border-radius: 5px;
            cursor: pointer;
        }
        .admin-container button:hover {
            background-color: #e0a800;
        }
    </style>
</head>
<body>
    <header>
        <div>
            <h2>Product Catalog</h2>
        </div>
        <div class="button-container">
            <!-- Check if user is logged in -->
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <!-- Profile button for logged-in users -->
                    <form action="profile.jsp" method="get" style="display: inline;">
                        <button type="submit">Profile</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <!-- Login button for non-logged-in users -->
                    <form action="login.jsp" method="get" style="display: inline;">
                        <button type="submit">Login</button>
                    </form>
                </c:otherwise>
            </c:choose>
            <form action="cart.jsp" method="get" style="display: inline;">
                <button type="submit" class="cart-button">Cart</button>
            </form>
        </div>
    </header>
    <h1>Browse Our Products</h1>
    <div class="product-container">
        <c:forEach var="product" items="${products}">
            <div class="product">
                <img src="${product.imagePath}" alt="${product.name}">
                <h3>${product.name}</h3>
                <p>Price: $${product.price}</p>
                <p>Category: ${product.category}</p>
                <form action="cart" method="post">
                    <input type="hidden" name="product_id" value="${product.id}">
                    <input type="hidden" name="name" value="${product.name}">
                    <input type="hidden" name="price" value="${product.price}">
                    <label for="quantity_${product.id}">Quantity:</label>
                    <input type="number" id="quantity_${product.id}" name="quantity" value="1" min="1">
                    <button type="submit" name="action" value="add">Add to Cart</button>
                </form>
                <a href="ProductController?action=details&id=${product.id}">View Details</a>
            </div>
        </c:forEach>
    </div>
    <div class="admin-container">
        <form action="admin.jsp" method="get">
            <button type="submit">Admin?</button>
        </form>
    </div>
</body>
</html>




