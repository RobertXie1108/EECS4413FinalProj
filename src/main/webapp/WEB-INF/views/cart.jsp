<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Shopping cart</title>
</head>
<body>
<%@ page import="model.Cart, model.CartItem" %>
<%@ page session="true" %>

<%
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null) {
        cart = new Cart();
        session.setAttribute("cart", cart);
    }
%>

<h1>Your Shopping Cart</h1>

<table border="1">
    <tr>
        <th>Product Name</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Total</th>
        <th>Action</th>
    </tr>

    <%
        for (CartItem item : cart.getItems()) {
    %>
    <tr>
        <td><%= item.getProductName() %></td>
        <td>$<%= item.getPrice() %></td>
        <td>
            <form action="CartServlet" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="productId" value="<%= item.getProductId() %>">
                <input type="number" name="quantity" value="<%= item.getQuantity() %>">
                <button type="submit">Update</button>
            </form>
        </td>
        <td>$<%= item.getPrice() * item.getQuantity() %></td>
        <td>
            <form action="CartServlet" method="post">
                <input type="hidden" name="action" value="remove">
                <input type="hidden" name="productId" value="<%= item.getProductId() %>">
                <button type="submit">Remove</button>
            </form>
        </td>
    </tr>
    <%
        }
    %>
</table>

<p>Total Price: $<%= cart.getTotalPrice() %></p>

<form action="CartServlet" method="post">
    <input type="hidden" name="action" value="clear">
    <button type="submit">Clear Cart</button>
</form>

<a href="catalog.jsp">Continue Shopping</a>
<a href="checkout.jsp">Proceed to Checkout</a>
</body>
</html>