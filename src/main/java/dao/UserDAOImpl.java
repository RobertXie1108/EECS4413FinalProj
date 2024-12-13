package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;

import model.User;

public class UserDAOImpl implements UserDAO {
	
	private String dbPath;
	
	public UserDAOImpl(ServletContext servletContext) {
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
	public boolean registerUser(User user) {
		String sql = "INSERT INTO users (username, password) VALUES (?,?)";
		
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeConnection(connection);
		}
	}

	@Override
	public User loginUser(String username, String password) {
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
		
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, username);
			statement.setString(2, password);
			try(ResultSet resultSet = statement.executeQuery()) {
				if(resultSet.next()) {
					User user = new User();
					user.setId(resultSet.getInt("id"));
					user.setUsername(resultSet.getString("username"));
					user.setPassword(resultSet.getString("password"));
					return user;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(connection);
		}
		return null;
	}
	
	@Override
	public boolean updateUser(User user) {
        String sql = "UPDATE users SET full_name = ?, shipping_address = ?, credit_card_number = ?, credit_card_expiry = ?, credit_card_cvv = ? WHERE id = ?";
	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	    	stmt.setString(1, user.getFullName());
	    	stmt.setString(2, user.getShippingAddress());
	    	stmt.setString(3, user.getCreditCardNumber());
	    	stmt.setString(4, user.getCreditCardExpiry());
	    	stmt.setString(5, user.getCreditCardCVV());
	    	stmt.setInt(6, user.getId());
            
	        return stmt.executeUpdate() > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	public User getUserById(int id) {
	    String sql = "SELECT * FROM users WHERE id = ?";
	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, id);

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                // Directly create the User object and set its properties
	                User user = new User();
	                user.setId(rs.getInt("id"));
	                user.setUsername(rs.getString("username"));
	                user.setPassword(rs.getString("password"));
	                user.setFullName(rs.getString("full_name"));
	                user.setShippingAddress(rs.getString("shipping_address"));
	                user.setCreditCardNumber(rs.getString("credit_card_number"));
	                user.setCreditCardExpiry(rs.getString("credit_card_expiry"));
	                user.setCreditCardCVV(rs.getString("credit_card_cvv"));
	                return user;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}
