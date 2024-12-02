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
            <!-- Product Image -->
            <img src="${product.imagePath}" alt="${product.name}">

            <!-- Product Info -->
            <div class="info">
                <h3>${product.name}</h3>
                <p><strong>Price:</strong> $${product.price}</p>
                <p><strong>Category:</strong> ${product.category}</p>
                <p><strong>Description:</strong> ${product.description}</p>
                <p><strong>Available Quantity:</strong> ${product.quantity}</p>
            </div>
        </div>

        <!-- Back Button -->
        <div class="back-button">
            <form action="/EECS4413FinalProject/ProductController?action=catalog" method="get">
    		<button type="submit">Back to Catalog</button>
			</form>
        </div>
    </div>
</body>
</html>
