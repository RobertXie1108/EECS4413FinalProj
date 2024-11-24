<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Checkout</title>
</head>
<body>
	<h1>Place Your Order</h1>
		<table border="1">
	    <tr>
	        <th>Product Name</th>
	        <th>Price</th>
	        <th>Quantity</th>
	        <th>Total</th>
	    </tr>
	    <c:forEach items="${cart.items}" var="item">
			 <tr>
				 <td> ${item.name} </td>
				 <td> ${item.price} </td>
				 <td> ${item.quantity} </td>
				 <td> ${item.totalPrice} </td>
			 </tr>
		 </c:forEach>
		 <tr>
		 	<td colspan='6'>Total Price: ${cart.totalPrice} </td>
	 	</tr>
 	</table>
 	<a href='cart.jsp'>Go Back</a>
 	<br>
 	<form method='post' action='OrderServlet'>
 		<p>
	 		<b>Enter Payment Information:</b> <br>
	 		Credit Card Number:
	 		<input type='text' name='cardNumber'><br>
	 		Security Code:
	 		<input type='text' name='cardCode'><br>
	 		Expiry Date:
	 		<input type='text' name='cardExpiryDate'><br>
 		</p>
 		<p>
	 		<br><b>Enter Shipping Information:</b><br>
	 		Name:
	 		<input type='text' name='customerName'><br>
	 		Phone Number:
	 		<input type='text' name='customerPhoneNumber'><br>
	 		Address:
	 		<input type='text' name='customerAddress'><br>
	 		City:
	 		<input type='text' name='customerCity'><br>
	 		Country:
	 		<input type='text' name='customerCountry'><br>
 		</p>
 		<br><input type='submit' value='Place Your Order'>
 	</form>
</body>
</html>