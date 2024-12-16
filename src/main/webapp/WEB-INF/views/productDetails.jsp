<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Detail</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7fc;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 60%;
            margin: 0 auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #333;
        }
        .product-detail {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 20px;
        }
        .product-detail img {
            width: 250px;
            height: 250px;
            object-fit: cover;
            border-radius: 8px;
        }
        .product-detail .info {
            text-align: center;
        }
        .product-detail .info h3 {
            font-size: 24px;
            color: #333;
        }
        .product-detail .info p {
            font-size: 18px;
            color: #555;
        }
        .add-to-cart {
            text-align: center;
            margin-top: 20px;
        }
        .add-to-cart form {
            display: inline-block;
        }
        .add-to-cart input[type="number"] {
            width: 60px;
            padding: 5px;
            font-size: 16px;
            margin-right: 10px;
        }
        .add-to-cart button {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 12px 20px;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
        }
        .add-to-cart button:hover {
            background-color: #218838;
        }
        .back-button {
            text-align: center;
            margin-top: 20px;
        }
        .back-button button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 12px 20px;
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
    <div class="container">
        <h2>Product Details</h2>
        <div class="product-detail">
            <img src="${product.imagePath}" alt="${product.name}">
            
            <div class="info">
                <h3>${product.name}</h3>
                <p><strong>Price:</strong> $${product.price}</p>
                <p><strong>Category:</strong> ${product.category}</p>
                <p><strong>Description:</strong> ${product.description}</p>
                <p><strong>Available Quantity:</strong> ${product.quantity}</p>
            </div>
        </div>

        <div class="add-to-cart">
            <form action="/EECS4413FinalProject/cart" method="post">
                <input type="hidden" name="product_id" value="${product.id}">
                <input type="hidden" name="name" value="${product.name}">
                <input type="hidden" name="price" value="${product.price}">
                <label for="quantity_${product.id}">Quantity:</label>
                <input type="number" id="quantity_${product.id}" name="quantity" value="1" min="1">
                <button type="submit" name="action" value="add">Add to Cart</button>
            </form>
        </div>

        <div class="back-button">
            <form action="/EECS4413FinalProject/ProductController?action=catalog" method="get">
                <button type="submit">Back to Home</button>
            </form>
        </div>
    </div>
</body>
</html>

