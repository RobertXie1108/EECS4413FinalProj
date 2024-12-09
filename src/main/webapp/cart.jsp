<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
            color: #333;
        }
        h1 {
            text-align: center;
            margin-top: 20px;
            color: #007bff;
        }
        .cart-container {
            width: 80%;
            margin: 20px auto;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            padding: 15px;
            border: 1px solid #ddd;
            text-align: center;
        }
        th {
            background-color: #f4f4f4;
            color: #333;
        }
        .total-row {
            font-weight: bold;
            background-color: #f4f4f4;
        }
        .empty-cart {
            text-align: center;
            margin: 50px 0;
            color: #777;
        }
        .cart-actions {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 20px;
        }
        .cart-actions a, .cart-actions button {
            padding: 10px 20px;
            font-size: 16px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            text-align: center;
        }
        .continue-shopping {
            background-color: #007bff;
            color: white;
        }
        .continue-shopping:hover {
            background-color: #0056b3;
        }
        .checkout-button {
            background-color: #28a745;
            color: white;
        }
        .checkout-button:hover {
            background-color: #218838;
        }
        .checkout-button:disabled {
            background-color: #ccc;
            cursor: not-allowed;
        }
        .remove-button {
        	background-color: #ff6666;
        	color: white;
        }
        .remove-button:hover {
        	background-color: #cc0000;
        	color: white;
        }
        .login-prompt {
            color: red;
            text-align: center;
        }
    </style>
</head>
<body>
    <h1>Your Shopping Cart</h1>
    <div class="cart-container">
        <c:choose>
            <c:when test="${not empty sessionScope.cart}">
                <table>
                    <tr>
                        <th>Product</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Subtotal</th>
                        <th></th>
                    </tr>
                    <c:forEach var="item" items="${sessionScope.cart}">
                        <tr>
                            <td>${item.product.name}</td>
                            <td>$ ${item.product.price}</td>
                            <td>${item.quantity}</td>
                            <td>$ ${item.quantity * item.product.price}</td>
                            <td><form action="cart" method="post">
                            	<input type="hidden" name="product_id" value="${item.product.id}">
                            	<button type="submit" class="remove-button" name="action" value="remove">Remove</button>
                            </form></td>
                            
                        </tr>
                    </c:forEach>
                    <tr class="total-row">
                        <td colspan="3" style="text-align: right;">Total:</td>
                        <td>$<c:set var="total" value="0" scope="page" />
                            <c:forEach var="item" items="${sessionScope.cart}">
                                <c:set var="total" value="${total + (item.quantity * item.product.price)}" scope="page" />
                            </c:forEach>
                            ${total}
                        </td>
                    </tr>
                </table>

                <!-- Check if user is logged in before allowing checkout -->
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <!-- User is logged in, show checkout button -->
                        <div class="cart-actions">
                            <form action="/EECS4413FinalProject/ProductController?action=catalog" method="get">
                                <button type="submit" class="continue-shopping">Continue shopping</button>
                            </form>
                            <form action="checkout.jsp" method="get" style="margin: 0;">
                                <button type="submit" class="checkout-button">Proceed to Checkout</button>
                            </form>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <!-- User is not logged in, show message and disable button -->
                        <p class="login-prompt">You need to <a href="login.jsp">log in</a> before proceeding to checkout.</p>
                        <div class="cart-actions">
                            <form action="/EECS4413FinalProject/ProductController?action=catalog" method="get">
                                <button type="submit" class="continue-shopping">Continue shopping</button>
                            </form>
                            <button class="checkout-button" disabled>Proceed to Checkout</button>
                        </div>
                    </c:otherwise>
                </c:choose>

            </c:when>
            <c:otherwise>
                <p class="empty-cart">Your cart is empty!</p>
                <div style="text-align: center; margin: 20px;">
                    <form action="/EECS4413FinalProject/ProductController?action=catalog" method="get">
                        <button type="submit" class="continue-shopping">Continue shopping</button>
                    </form>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>


