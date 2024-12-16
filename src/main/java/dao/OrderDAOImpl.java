package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		String insertOrderSQL = "INSERT INTO orders (user_id, order_date, total_amount) VALUES (?, CURRENT_TIMESTAMP, ?)";
        String insertOrderItemsSQL = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false); // Enable transaction
            // Insert into orders
            PreparedStatement orderStmt = connection.prepareStatement(insertOrderSQL, new String[] {"order_id"});
            orderStmt.setInt(1, order.getUserId());
            orderStmt.setDouble(2, order.getTotalPrice());
            orderStmt.executeUpdate();

            // Get the generated order ID
            ResultSet rs = orderStmt.getGeneratedKeys();
            if (!rs.next()) {
                connection.rollback();
                return false;
            }
            int orderId = rs.getInt(1);
            // Insert into order_items
            PreparedStatement itemsStmt = connection.prepareStatement(insertOrderItemsSQL);
            for (CartItem item : order.getItems()) {
                itemsStmt.setInt(1, orderId);
                itemsStmt.setInt(2, item.getProduct().getId());
                itemsStmt.setInt(3, item.getQuantity());
                itemsStmt.setDouble(4, item.getProduct().getPrice());
                itemsStmt.addBatch();
            }
            itemsStmt.executeBatch();
            connection.commit(); // Commit transaction
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public Order getOrderById(int orderId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Order> getAllOrders() {
	    List<Order> orders = new ArrayList<>();
	    String sql = "SELECT o.*, u.username FROM orders o JOIN users u ON o.user_id = u.id";

	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql);
	         ResultSet rs = statement.executeQuery()) {

	        while (rs.next()) {
	            Order order = new Order();
	            order.setId(rs.getInt("id"));
	            order.setUserId(rs.getInt("user_id"));
	            order.setTotalPrice(rs.getDouble("total_amount"));
	            order.setOrderDate(rs.getString("order_date"));
	            order.setUsername(rs.getString("username")); // Set the username
	            orders.add(order);
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return orders;
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
	public List<Order> getOrdersByCustomer(String username) {
	    List<Order> orders = new ArrayList<>();
	    String sql = "SELECT orders.*, users.username " +
	                 "FROM orders " +
	                 "JOIN users ON orders.user_id = users.id " +
	                 "WHERE users.username = ?";

	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setString(1, username);

	        try (ResultSet rs = statement.executeQuery()) {
	            while (rs.next()) {
	                orders.add(mapRowToOrder(rs));
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return orders;
	}

	
	@Override
	public List<Order> getOrdersByProduct(String productName) {
		List<Order> orders = new ArrayList<>();
	    String sql = "SELECT DISTINCT orders.*, users.username " +
	                 "FROM orders " +
	                 "JOIN users ON orders.user_id = users.id " +
	                 "JOIN order_items ON orders.id = order_items.order_id " +
	                 "JOIN product ON order_items.product_id = product.id " +
	                 "WHERE product.name = ?";

	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setString(1, productName);

	        try (ResultSet rs = statement.executeQuery()) {
	            while (rs.next()) {
	                orders.add(mapRowToOrder(rs));
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return orders;
	}
	
	@Override
	public List<Order> getOrdersByDate(String date) {
		List<Order> orders = new ArrayList<>();
	    String sql = "SELECT orders.*, users.username " +
	                 "FROM orders " +
	                 "JOIN users ON orders.user_id = users.id " +
	                 "WHERE DATE(orders.order_date) = DATE(?)";

	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setString(1, date);

	        try (ResultSet rs = statement.executeQuery()) {
	            while (rs.next()) {
	                orders.add(mapRowToOrder(rs));
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return orders;
	}

	private Order mapRowToOrder(ResultSet rs) throws SQLException {
	    Order order = new Order();
	    order.setId(rs.getInt("id"));
	    order.setUserId(rs.getInt("user_id"));
	    order.setTotalPrice(rs.getDouble("total_amount"));
	    order.setOrderDate(rs.getString("order_date"));
	    order.setUsername(rs.getString("username"));
	    return order;
	}





}