package dao;

import java.util.List;

import model.Order;

public interface OrderDAO {
    boolean placeOrder(Order order);
    List<Order> getOrdersByUserId(int userId);
    Order getOrderById(int orderId);
    List<Order> getAllOrders();
}
