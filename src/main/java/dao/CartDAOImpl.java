package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;

import model.Cart;

public class CartDAOImpl implements CartDAO {
	private String dbPath;
	
	public CartDAOImpl(ServletContext servletContext) {
        this.dbPath = servletContext.getRealPath("/EStore.db");
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
	public boolean addItemToCart(Cart cart) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateCartItem(Cart cart) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeItemFromCart(int cartId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Cart> getCartItemsByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean clearCart(int userId) {
		// TODO Auto-generated method stub
		return false;
	}

}
