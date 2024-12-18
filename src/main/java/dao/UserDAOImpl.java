package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;

import model.User;

/*
 * This DAO implementation handles database operations related to "User" entity
 * Features one Create method, two Read methods, one Update method
 */
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

	/*
	 * Creates a new user in database
	 * @param user The user to add
	 * @return True if successful, false otherwise
	 */
	@Override
	public boolean registerUser(User user) {
		String sql = "INSERT INTO users (username, password) VALUES (?,?)";
		
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			
			boolean result = statement.executeUpdate() > 0;
			
			System.out.println("Create User Result: " + result);
			System.out.println("DB Path: " + dbPath);
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeConnection(connection);
		}
	}

	/*
	 * Retrieves user given log in information (username, password)
	 * @param username Username of user to retrieve
	 * @param password Password of user to retrieve
	 * @return User with id, username, and password. Null if not possible.
	 */
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
	
	/*
	 * Updates user in database
	 * @param user User with updated data
	 * @return True if successful, otherwise false
	 */
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
            
	        boolean result = stmt.executeUpdate() > 0;
	        
	        System.out.println("Update User Result: " + result);
			System.out.println("DB Path: " + dbPath);
			
			return result;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	/*
	 * Retrieves user given an id
	 * @param id The id of the user to retrieve
	 * @return True if successful, otherwise false
	 */
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
	                user.setCreditCardCVV(rs.getString("credit_card_cvv"));	// Set attributes
	                return user;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}
