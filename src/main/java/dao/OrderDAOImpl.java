package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import model.CartItem;
import model.Order;
import model.Product;
public class OrderDAOImpl implements OrderDAO {
	private String dbPath;
	
	public OrderDAOImpl(ServletContext servletContext) {
        this.dbPath = servletContext.getRealPath("/Estore.db");
    }
	
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:sqlite:" + dbPath);
	}
	private void closeConnection(Connection connection) {
		if (connection == null)
			return;
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean placeOrder(Order order) {
	    String insertOrderSql = "INSERT INTO orders (user_id, total_amount, order_date) VALUES (?, ?, ?)";
	    String insertOrderItemSql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
	    boolean isOrderPlaced = false;

	    try (Connection connection = getConnection()) {
	        connection.setAutoCommit(false); // Begin transaction

	        // Insert order into "orders" table
	        try (PreparedStatement orderStmt = connection.prepareStatement(insertOrderSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
	            orderStmt.setInt(1, order.getUserId());
	            orderStmt.setDouble(2, order.getTotalPrice());
	            orderStmt.setString(3, LocalDateTime.now().toString());
	            orderStmt.executeUpdate();

	            // Retrieve generated order ID
	            ResultSet rs = orderStmt.getGeneratedKeys();
	            int orderId = 0;
	            if (rs.next()) {
	                orderId = rs.getInt(1);
	            } else {
	                throw new SQLException("Failed to retrieve order ID");
	            }

	            // Insert items into "order_items" table
	            try (PreparedStatement itemStmt = connection.prepareStatement(insertOrderItemSql)) {
	                for (CartItem item : order.getItems()) {
	                    itemStmt.setInt(1, orderId);
	                    itemStmt.setInt(2, item.getProduct().getId());
	                    itemStmt.setInt(3, item.getQuantity());
	                    itemStmt.setDouble(4, item.getProduct().getPrice());
	                    itemStmt.addBatch();
	                }
	                itemStmt.executeBatch();
	            }

	            connection.commit(); // Commit transaction
	            isOrderPlaced = true;
	        } catch (SQLException e) {
	            connection.rollback(); // Rollback transaction on failure
	            throw e;
	        } finally {
	            connection.setAutoCommit(true); // Restore default behavior
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return isOrderPlaced;
	}
	
	@Override
	public List<Order> getCustomerOrders(int userId) {
	    List<Order> orders = new ArrayList<>();
	    String sql = "SELECT id, total_amount, order_date FROM orders WHERE user_id = ?";

	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setInt(1, userId);
	        ResultSet rs = statement.executeQuery();

	        while (rs.next()) {
	            Order order = new Order();
	            order.setId(rs.getInt("id"));
	            order.setUserId(userId);
	            order.setTotalPrice(rs.getDouble("total_amount"));
	            order.setOrderDate(rs.getString("order_date"));
	            // Fetch items for this order
	            orders.add(order);
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return orders;
	}
	
	@Override
	public List<CartItem> getOrderItems(int orderId) {
	    List<CartItem> items = new ArrayList<>();
	    String query = "SELECT oi.product_id, p.name, p.image_url, p.category, oi.quantity, oi.price " +
	                   "FROM order_items oi " +
	                   "JOIN product p ON oi.product_id = p.id " +
	                   "WHERE oi.order_id = ?";

	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setInt(1, orderId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                // Create a Product object and populate its fields
	                Product product = new Product();
	                product.setId(rs.getInt("product_id"));
	                product.setName(rs.getString("name"));
	                product.setImagePath(rs.getString("image_url"));
	                product.setCategory(rs.getString("category"));

	                // Create a CartItem object using the Product
	                CartItem item = new CartItem(product, rs.getInt("quantity"));
	                item.setPrice(rs.getDouble("price")); // Set the price
	                items.add(item); // Add the CartItem to the list
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return items;
	}


	
	@Override
	public Order getOrderById(int orderId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Order> getAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}
}