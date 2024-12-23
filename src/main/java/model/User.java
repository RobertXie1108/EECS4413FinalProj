package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/*
 * JavaBean Class to represent a user in the system.
 * includes an id, unique username, password, full name, shipping address, credit card number,
 * credit card expiry date, credit card security code, and order history of the user
 */
public class User implements Serializable{
    private int id;
    private String username;
    private String password;
    private String fullName;
    private String shippingAddress;
    private String creditCardNumber;
    private String creditCardExpiry;
    private String creditCardCVV;
    private List<Order> orderHistory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardExpiry() {
        return creditCardExpiry;
    }

    public void setCreditCardExpiry(String creditCardExpiry) {
        this.creditCardExpiry = creditCardExpiry;
    }

    public String getCreditCardCVV() {
        return creditCardCVV;
    }

    public void setCreditCardCVV(String creditCardCVV) {
        this.creditCardCVV = creditCardCVV;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }
}

