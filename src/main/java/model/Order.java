package model;

import java.io.Serializable;
import java.util.List;
/*
 * JavaBean Class to represent an order made by the user.
 * Includes the id of the order, the id of the user who made the purchase,
 * total price, a list of items, date of the order, and the username attached to the order.
 */
public class Order implements Serializable{
    private int id;
    private int userId;
    private double totalPrice;
    private List<CartItem> items;
    private String orderDate;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
    
    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    
}
