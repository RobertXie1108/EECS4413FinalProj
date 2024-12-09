package model;

import java.util.List;

public class Order {
    private int id;
    private int userId;
    private double totalPrice;
    private List<CartItem> items;
    private PaymentInformation paymentInformation;
    private ShippingInformation shippingInformation;

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
    
    public PaymentInformation getPaymentInformation() {
    	return this.paymentInformation;
    }
    
    public void setPaymentInformation(PaymentInformation paymentInformation) {
    	this.paymentInformation = paymentInformation;
    }
    
    public ShippingInformation getShippingInformation() {
    	return this.shippingInformation;
    }
    
    public void setShippingInformation(ShippingInformation shippingInformation) {
    	this.shippingInformation = shippingInformation;
    }
}
