<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Order History</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f7fc;
        }
        h1 {
            text-align: center;
            margin: 20px 0;
            color: #007bff;
            font-size: 28px;
        }
        .order-container {
            width: 90%;
            max-width: 1200px;
            margin: 20px auto;
        }
        .order {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
            padding: 20px;
            border-left: 5px solid #007bff;
        }
        .order h2 {
            margin: 0;
            color: #333;
            font-size: 20px;
        }
        .order p {
            margin: 5px 0;
            font-size: 16px;
            color: #555;
        }
        .order table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }
        .order table th, .order table td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        .order table th {
            background-color: #f4f4f4;
            font-size: 16px;
        }
        .order table td {
            font-size: 15px;
        }
        .order .total {
            text-align: right;
            font-size: 18px;
            font-weight: bold;
            margin-top: 10px;
            color: #007bff;
        }
        .back-button {
            text-align: center;
            margin: 30px 0;
        }
        .back-button button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 12px 20px;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        .back-button button:hover {
            background-color: #0056b3;
        }
        .no-orders {
            text-align: center;
            color: #777;
            font-size: 18px;
            margin-top: 50px;
        }
    </style>
</head>
<body>
    <h1>Admin Sales History</h1>
    <div class="order-container">
        <c:choose>
            <c:when test="${not empty orders}">
                <c:forEach var="order" items="${orders}">
                    <div class="order">
                        <h2>Order ID: ${order.id}</h2>
                        <p><strong>Customer ID:</strong> ${order.userId}</p>
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
                <p class="no-orders">No orders found.</p>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="back-button">
        <form action="admin.jsp" method="get">
            <button type="submit">Back to Admin Dashboard</button>
        </form>
    </div>
</body>
</html>