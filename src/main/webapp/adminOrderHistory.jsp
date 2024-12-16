<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sales History</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f7fc;
        }
        h1 {
            text-align: center;
            margin-top: 20px;
            color: #007bff;
        }
        .filters {
            width: 90%;
            margin: 20px auto;
            padding: 10px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            display: flex;
            gap: 20px;
            justify-content: space-around;
        }
        .filters form {
            display: flex;
            gap: 10px;
            align-items: center;
        }
        .filters label {
            font-size: 14px;
            font-weight: bold;
        }
        .filters input[type="text"], .filters input[type="date"], .filters select {
            padding: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
        }
        .filters button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }
        .filters button:hover {
            background-color: #0056b3;
        }
        .order-container {
            width: 90%;
            margin: 20px auto;
        }
        .order {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
            padding: 20px;
        }
        .order h2 {
            color: #333;
            margin-bottom: 10px;
        }
        .order table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 10px;
        }
        .order table th, .order table td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
        }
        .order table th {
            background-color: #f4f4f4;
        }
        .order .total {
            text-align: right;
            font-size: 16px;
            font-weight: bold;
            margin-top: 10px;
        }
        .back-button {
            display: block;
            text-align: center;
            margin: 20px;
        }
        .back-button button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 20px;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
        }
        .back-button button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h1>Sales History</h1>

    <div class="filters">
        <form action="AdminOrderController" method="get">
            <label for="customer">Username:</label>
            <input type="text" id="customer" name="customer" placeholder="Username">
            <button type="submit" name="action" value="filterByCustomer">Filter</button>
        </form>
        <form action="AdminOrderController" method="get">
            <label for="product">Product:</label>
            <input type="text" id="product" name="product" placeholder="Product Name">
            <button type="submit" name="action" value="filterByProduct">Filter</button>
        </form>
        <form action="AdminOrderController" method="get">
            <label for="date">Order Date:</label>
            <input type="date" id="date" name="date">
            <button type="submit" name="action" value="filterByDate">Filter</button>
        </form>
        <form action="AdminOrderController" method="get">
        	<button type="submit" name="action" value="viewAll" style="margin-top: 10px;">Clear Filters</button>
    	</form>
    </div>

    <div class="order-container">
        <c:choose>
            <c:when test="${not empty orders}">
                <c:forEach var="order" items="${orders}">
                    <div class="order">
                        <h2>Order ID: ${order.id}</h2>
                        <p><strong>Customer ID:</strong> ${order.userId}</p>
                        <p><strong>Username:</strong> ${order.username}</p>
                        <p><strong>Order Date:</strong> ${order.orderDate}</p>
                        <table>
                            <thead>
                                <tr>
                                    <th>Product Name</th>
                                    <th>Quantity</th>
                                    <th>Item Total Price</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${order.items}">
                                    <tr>
                                        <td>${item.product.name}</td>
                                        <td>${item.quantity}</td>
                                        <td>$ ${item.quantity * item.price}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div class="total">
                            <strong>Order Total: $ ${order.totalPrice}</strong>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p style="text-align: center; color: #555;">No orders found.</p>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="back-button">
        <form action="admin.jsp" method="get">
            <button type="submit">Back to Dashboard</button>
        </form>
    </div>
</body>
</html>
