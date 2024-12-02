package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;

import model.CartItem;
import model.Order;

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
            var rs = orderStmt.getGeneratedKeys();
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
	public List<Order> getOrdersByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
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
