<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order History</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
            color: #333;
        }
        .order-history {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
        }
        .order-history th, .order-history td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        .order-history th {
            background-color: #007bff;
            color: white;
        }
        .order-items {
            margin-left: 20px;
            font-size: 14px;
        }
        .order-items th, .order-items td {
            padding: 5px;
        }
        h1 {
            text-align: center;
            margin: 20px;
        }
    </style>
</head>
<body>
    <h1>Order History</h1>
    <c:choose>
        <c:when test="${not empty orders}">
            <table class="order-history">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Date</th>
                        <th>Total Amount</th>
                        <th>Details</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td>${order.id}</td>
                            <td>${order.orderDate}</td>
                            <td>$${order.totalPrice}</td>
                            <td>
                                <table class="order-items">
                                    <thead>
                                        <tr>
                                            <th>Product</th>
                                            <th>Quantity</th>
                                            <th>Price</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="item" items="${order.items}">
                                            <tr>
                                                <td>${item.product.name}</td>
                                                <td>${item.quantity}</td>
                                                <td>$${item.price}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p style="text-align: center;">You have no orders yet!</p>
        </c:otherwise>
    </c:choose>
</body>
</html>


