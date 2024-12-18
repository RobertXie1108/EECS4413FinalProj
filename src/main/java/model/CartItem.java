package model;

import java.io.Serializable;
/* 
 * JavaBean class to represent an item in the cart.
 * Includes the product, the quantity of it in the cart, and the price.
 */
public class CartItem implements Serializable{
    private Product product;
    private int quantity;
    private double price;


    public CartItem(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
