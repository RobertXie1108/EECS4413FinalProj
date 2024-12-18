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

/*
 * This DAO implementation handles database operations related to the "Order" entity.
 * Features one Create method, six Read methods, and one utility method
 */
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
	/*
	 * Creates a new order in the database
	 * @param order The order to create
	 * @return True if creation was successful, else false
	 */
	@Override
	public boolean placeOrder(Order order) {
		String insertOrderSQL = "INSERT INTO orders (user_id, order_date, total_amount) VALUES (?, CURRENT_TIMESTAMP, ?)";
        String insertOrderItemsSQL = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false); // Enable transaction
            // Insert new order into orders
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
            // Insert purchased items from order into order_items
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
            System.out.println("Create Order Success!");
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
	
	/*
	 * Retrieves all orders in the database
	 * @return List of Order objects
	 */
	@Override
	public List<Order> getAllOrders() {
	    List<Order> orders = new ArrayList<>();
	    String sql = "SELECT o.*, u.username FROM orders o JOIN users u ON o.user_id = u.id";

	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql);
	         ResultSet rs = statement.executeQuery()) {

	        while (rs.next()) {
	            Order order = new Order();	// Create a new Order instance
	            order.setId(rs.getInt("id"));	// Set Attributes
	            order.setUserId(rs.getInt("user_id"));
	            order.setTotalPrice(rs.getDouble("total_amount"));
	            order.setOrderDate(rs.getString("order_date"));
	            order.setUsername(rs.getString("username"));
	            orders.add(order);	// Add to list
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return orders;
	}

	/*
	 * Retrieves orders pertaining to a particular customer given the ID
	 * @param userId The ID of the user to retrieve
	 * @return List of Order objects
	 */
	@Override
	public List<Order> getCustomerOrders(int userId) {
	    List<Order> orders = new ArrayList<>();
	    String sql = "SELECT id, total_amount, order_date FROM orders WHERE user_id = ?";

	    try (Connection connection = getConnection();
	        PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setInt(1, userId); 
	        ResultSet rs = statement.executeQuery();

	        while (rs.next()) {		// For each order found in the query, create a new Order and set attributes
	            Order order = new Order();
	            order.setId(rs.getInt("id"));
	            order.setUserId(userId);
	            order.setTotalPrice(rs.getDouble("total_amount"));
	            order.setOrderDate(rs.getString("order_date"));
	            // Fetch items for this order
	            orders.add(order);	// Add Order to list
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return orders;
	}
	
	/*
	 * Retrieves order items from an order
	 * @param orderId The ID of the order to retrieve
	 * @return List of CartItem objects
	 */
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
	/*
	 * Retrieve all orders by customer given the username
	 * Uses a JOIN and slightly different use case than getCustomerOrders()
	 * @param username The unique username of user (not in orders table)
	 * @return List of Order objects
	 */
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
	                orders.add(mapRowToOrder(rs));	// Create new Order from result set and add to list
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return orders;
	}

	/*
	 * Retrieves all orders of a product
	 * @param productName The name of the product
	 * @return List of Order objects
	 */
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
	                orders.add(mapRowToOrder(rs));	// Create new Order from result set and add to list
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return orders;
	}
	
	/*
	 * Retrieves all orders based on a date
	 * @param date String representing the date to query by
	 * @return List of Order objects
	 */
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
	                orders.add(mapRowToOrder(rs));	// Create new Order from result set and add to list
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return orders;
	}

	/*
	 * Utility function that creates an Order instance based on the given result set
	 * @param rs The result set to retrieve information from
	 * @return Order instance
	 */
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