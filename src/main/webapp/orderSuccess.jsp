<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Order Success</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            color: #333;
            text-align: center;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 50%;
            margin: 100px auto;
            background-color: white;
            padding: 20px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }
        h1 {
            color: #28a745;
        }
        p {
            font-size: 18px;
        }
        a {
            color: #007bff;
            text-decoration: none;
            font-size: 16px;
        }
        a:hover {
            text-decoration: underline;
        }
        .continue-shopping {
            padding: 12px 30px;
            font-size: 18px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }
        .continue-shopping:hover {
            background-color: #0056b3;
            transform: scale(1.05);
        }
        .continue-shopping:focus {
            outline: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Order Successfully Placed!</h1>
        <p>Thank you for your purchase! Your order has been confirmed, and you will receive a confirmation email shortly.</p>
        <p>Your order details have been sent to your email.</p>
        <form action="/EECS4413FinalProject/ProductController?action=catalog" method="get">
            <button type="submit" class="continue-shopping">Continue Shopping</button>
        </form>
    </div>
</body>
</html>