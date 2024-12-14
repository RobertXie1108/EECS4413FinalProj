<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>ChippyChips Jersey Store</title>
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
            transform: scale(1.05);
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
            transform: scale(1.05);
        }
        .category-bar {
            display: flex;
            justify-content: center;
            background-color: #e9ecef;
            padding: 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        .category-bar form {
            margin: 0 10px;
        }
        .category-bar button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
        }
        .category-bar button:hover {
            background-color: #0056b3;
            transform: scale(1.05);
        }
        .sort-container {
            display: flex;
            justify-content: flex-end;
            align-items: center;
            padding: 10px 20px;
            background-color: #f8f9fa;
        }
        .sort-container label {
            font-size: 14px;
            margin-right: 10px;
        }
        .sort-container select {
            font-size: 14px;
            padding: 5px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        .sort-button {
    		background-color: #007bff;
    		color: white;
   			border: none;
    		padding: 8px 16px;
    		font-size: 14px;
    		border-radius: 5px;
    		cursor: pointer;
    		transition: background-color 0.3s ease, transform 0.2s ease;
		}
		.sort-button:hover {
    		background-color: #0056b3;
    		transform: scale(1.05);
		}
        .search-container {
            text-align: center;
            margin: 20px 0;
        }
        .search-container input[type="text"] {
            width: 300px;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .search-container button {
            padding: 10px 15px;
            font-size: 16px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .search-container button:hover {
            background-color: #0056b3;
            transform: scale(1.05);
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
            transform: scale(1.05);
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
    <header>
        <div>
            <h2>ChippyChips Jersey Store</h2>
        </div>
        <div class="button-container">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <form action="profile.jsp" method="get" style="display: inline;">
                        <button type="submit">Profile</button>
                    </form>
                </c:when>
                <c:otherwise>
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
    <div class="category-bar">
        <form action="ProductController" method="get">
            <input type="hidden" name="action" value="filter">
            <input type="hidden" name="category" value="NBA">
            <button type="submit">NBA</button>
        </form>
        <form action="ProductController" method="get">
            <input type="hidden" name="action" value="filter">
            <input type="hidden" name="category" value="NHL">
            <button type="submit">NHL</button>
        </form>
        <form action="ProductController" method="get">
            <input type="hidden" name="action" value="filter">
            <input type="hidden" name="category" value="MLB">
            <button type="submit">MLB</button>
        </form>
        <form action="ProductController" method="get">
            <input type="hidden" name="action" value="filter">
            <input type="hidden" name="category" value="NFL">
            <button type="submit">NFL</button>
        </form>
        <form action="ProductController" method="get">
            <input type="hidden" name="action" value="filter">
            <input type="hidden" name="category" value="All">
            <button type="submit">All</button>
        </form>
    </div>
    <div class="search-container">
        <form action=search method="get">
            <input type="hidden" name="action" value="search">
            <input type="text" name="keyword" placeholder="Search for products...">
            <button type="submit">Search</button>
        </form>
    </div>
    <div class="sort-container">
        <form action="ProductController" method="get">
    	<label for="sortBy">Sort by:</label>
    	<select name="sortBy" id="sortBy">
        	<option value="price">Price</option>
       		<option value="name">Name</option>
       		<option value="category">Category</option>
    	</select>
    	<label for="sortOrder">Order:</label>
    	<select name="sortOrder" id="sortOrder">
       		<option value="asc">Ascending</option>
       		<option value="desc">Descending</option>
    		</select>
		<button type="submit" name="action" value="sort" class="sort-button">Apply</button>
	</form>
    </div>
    
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
        <div class="footer">
        <p>&copy; ChippyChips. All rights reserved.</p>
    </div>
</body>
</html>




