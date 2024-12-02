<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            flex-direction: column;
            align-items: flex-end;
        }
        header .button-container button {
            margin-top: 10px;
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
    </style>
</head>
<body>
    <header>
        <div>
            <h2>Product Catalog</h2>
        </div>
        <div class="button-container">
            <form action="login.jsp" method="get" style="display: inline;">
                <button type="submit">Login</button>
            </form>
            <form action="admin.jsp" method="get" style="display: inline;">
                <button type="submit">Admin?</button>
            </form>
        </div>
    </header>
    <h1>Browse Our Products</h1>
    <div class="product-container">
        <c:forEach var="product" items="${products}">
            <div class="product">
                <img src="${pageContext.request.contextPath}/${product.imagePath}" alt="${product.name}">

                <h3>${product.name}</h3>
                <p>Price: $${product.price}</p>
                <p>Category: ${product.category}</p>
                <form action="cart" method="post">
                    <input type="hidden" name="product_id" value="${product.id}">
                    <label for="quantity_${product.id}">Quantity:</label>
                    <input type="number" id="quantity_${product.id}" name="quantity" value="1" min="1">
                    <button type="submit" name="action" value="add">Add to Cart</button>
                </form>
                <a href="ProductController?action=details&id=${product.id}">View Details</a>
            </div>
        </c:forEach>
    </div>
</body>
</html>


