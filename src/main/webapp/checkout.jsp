<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
            color: #333;
        }
        .container {
            width: 80%;
            margin: 20px auto;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: center;
        }
        th {
            background-color: #f4f4f4;
        }
        .total {
            font-size: 18px;
            font-weight: bold;
            text-align: right;
            margin-top: 10px;
        }
        .payment-info {
		    font-family: Arial, sans-serif;
		    margin-bottom: 20px;
		}
				
		.payment-info .input-group {
		    display: flex;
		    gap: 10px;
		    align-items: center;
		}
		.payment-info input {
		    padding: 8px;
		    font-size: 14px;
		    border: 1px solid #ccc;
		    border-radius: 4px;
		    outline: none;
		}
		.payment-info input:focus {
		    border-color: #007BFF; /* Highlighted border */
		    box-shadow: 0 0 4px rgba(0, 123, 255, 0.5);
		}
		#ccnumber {
		    width: 50%;
		}
		#ccexpiry, #cccvv {
			width: 25%;
		}
        .checkout-button {
            display: block;
            width: 100%;
            padding: 10px;
            background-color: #28a745;
            color: white;
            border: none;
            text-align: center;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
            text-decoration: none;
        }
        .checkout-button:hover {
            background-color: #218838;
        }
        .back-link {
            display: inline-block;
            margin-top: 10px;
            text-decoration: none;
            color: #007bff;
            font-size: 14px;
        }
        .back-link:hover {
            text-decoration: underline;
        }
        form input, form select {
            width: 100%;
            padding: 10px;
            font-size: 14px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Order Review</h1>
        <c:choose>
            <c:when test="${not empty sessionScope.cart}">
                <table>
                    <tr>
                        <th>Product</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Subtotal</th>
                    </tr>
                    <c:forEach var="item" items="${sessionScope.cart}">
                        <tr>
                            <td>${item.product.name}</td>
                            <td>$ ${item.product.price}</td>
                            <td>${item.quantity}</td>
                            <td>$ ${item.quantity * item.product.price}</td>
                        </tr>
                    </c:forEach>
                </table>
                <div class="total">
                    Total: $ 
                    <c:set var="total" value="0" scope="page"/>
                    <c:forEach var="item" items="${sessionScope.cart}">
                        <c:set var="total" value="${total + (item.quantity * item.product.price)}" scope="page"/>
                    </c:forEach>${total}
                </div>

                <!-- Shipping and Payment Information Form -->
                <h2>Shipping and Payment Information</h2>
                <form action="OrderController" method="post">
                    <input type="hidden" name="action" value="checkout">
                    <input type="hidden" name="totalAmount" value="${total}">

                    <!-- Email -->
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required placeholder="Enter your email address">

                    <!-- Shipping Address -->
                    <label for="address">Shipping Address:</label>
                    <input type="text" id="address" name="address" value="${user.shippingAddress}" required placeholder="Enter your shipping address">

                    <!-- Payment Information -->
                    <div class="payment-info">
					    <label for="paymentMethod">Payment Information:</label>
					    <div class="input-group">
					        <input type="text" id="ccnumber" name="ccnumber" value="${user.creditCardNumber}" 
					               required placeholder="Credit Card Number">
					        <input type="text" id="ccexpiry" name="ccexpiry" value="${user.creditCardExpiry}" 
					               required placeholder="MM/YY">
					        <input type="text" id="cccvv" name="cccvv" value="${user.creditCardCVV}" 
					               required placeholder="CVV">
					    </div>
					</div>
					
                    <button type="submit" class="checkout-button">Confirm & Checkout</button>
                </form>
            </c:when>
            <c:otherwise>
                <p>Your cart is empty! <a href="home.jsp">Go back to shopping</a>.</p>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>

