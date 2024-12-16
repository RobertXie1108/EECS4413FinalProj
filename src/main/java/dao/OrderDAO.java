package dao;

import java.util.List;

import model.CartItem;
import model.Order;

public interface OrderDAO {
    boolean placeOrder(Order order);
    public List<Order> getCustomerOrders(int customerId);
    Order getOrderById(int orderId);
    List<Order> getAllOrders();
	List<CartItem> getOrderItems(int orderId);
	public List<Order> getOrdersByCustomer(String customerName);
	public List<Order> getOrdersByProduct(String productName);
	public List<Order> getOrdersByDate(String date);
}
