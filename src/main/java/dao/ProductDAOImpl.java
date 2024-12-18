package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import model.Product;

/*
 * This DAO implementation handles database operations related to the "Product" entity.
 * Relevant to primarily home page and admin page
 * Features one Create method, one Update method, five Read methods, one Delete method
 */
public class ProductDAOImpl implements ProductDAO {
	private String dbPath;
	
	public ProductDAOImpl(ServletContext servletContext) {
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
		//System.out.println(dbPath); // Debugging
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
	 * Retrieves all products in database
	 * @return List of Product objects
	 */
	@Override
	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<>();
		String sql = "SELECT * FROM product";
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				products.add(mapRowToProduct(rs));	// Create Product from resultset and add to list
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return products;
	}
	
	/*
	 * Retrieves a product given an ID
	 * @param id The ID of the product to retrieve
	 * @return Product if found or null if not found
	 */
	@Override
	public Product getProductById(int id) {
		String sql = "SELECT * FROM product WHERE id = ?";
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, id);
			
			try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapRowToProduct(rs);	// Creates Product from result set and returns
                }
            }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return null;	// Otherwise return null
	}
	/*
	 * Retrieves products with names, category or description similar to the keyword
	 * @param keyWord The keyword to search by
	 * @return List of Product objects
	 */
	@Override
	public List<Product> searchProducts(String keyWord) {
	    List<Product> result = new ArrayList<>();
	    String sql = "SELECT * FROM product " +
	                 "WHERE name LIKE ? " +
	                 "OR description LIKE ? " +
	                 "OR category LIKE ?";
	    String searchKey = "%" + keyWord.trim() + "%";
	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setString(1, searchKey);
	        statement.setString(2, searchKey);
	        statement.setString(3, searchKey);
	        try (ResultSet rs = statement.executeQuery()) {
	            while (rs.next()) {
	                Product product = mapRowToProduct(rs);	// Create Product from query and adds to list
	                result.add(product);
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace(); 
	    }
	    return result;
	}
	
	/*
	 * Retrieves all products with the given category
	 * @param category The category to search by
	 * @return List of Product objects
	 */
	@Override
	public List<Product> filterProductsByCategory(String category) {
	    List<Product> result = new ArrayList<>();
	    String sql = "SELECT * FROM product WHERE category = ?";
	    
	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {
	        
	        statement.setString(1, category);
	        try (ResultSet rs = statement.executeQuery()) {
	            while (rs.next()) {
	                Product product = mapRowToProduct(rs);	// Create Product from query and add to list
	                result.add(product);
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace(); 
	    }
	    return result;
	}
	
	/*
	 * Retrieves all products but sorted by a given attribute
	 * @param attribute The attribute to sort by
	 * @param ascending Boolean to decide the way the results should be sorted
	 */
	@Override
	public List<Product> sortProductsBy(String attribute, boolean ascending) {
	    List<Product> result = new ArrayList<>();
	    String asc;
	    
	    if (!attribute.equalsIgnoreCase("price") && !attribute.equalsIgnoreCase("name") && !attribute.equalsIgnoreCase("category")) {
	        throw new IllegalArgumentException("Invalid attribute: " + attribute);
	    }
	    
	    if(ascending == true) {
	    	asc = " ASC";
	    }
	    else {
	    	asc = " DESC";
	    }
	    
	    String sql = "SELECT * FROM product ORDER BY " + attribute + asc;
	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql);
	         ResultSet rs = statement.executeQuery()) {
	        while (rs.next()) {
	            Product product = mapRowToProduct(rs); // Create Product from query and add to list
	            result.add(product);
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace(); 
	    }
	    return result;
	}
	/*
	 * Creates a new Product and adds it to the database
	 * @param product The product to add
	 * @return boolean True if successful, otherwise false
	 */
	@Override
	public boolean addProduct(Product product) {
		String sql = "INSERT INTO product (name, description, category, price, image_url, quantity) VALUES (?, ?, ?, ?, ?, ?)";
	    try (
	    	Connection connection = getConnection();
	        PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setString(1, product.getName());
	        statement.setString(2, product.getDescription());
	        statement.setString(3, product.getCategory());
	        statement.setDouble(4, product.getPrice());
	        statement.setString(5, product.getImagePath());
	        statement.setInt(6, product.getQuantity());	// Set attributes
	        
	        boolean result = statement.executeUpdate() > 0;
	        
	        System.out.println("Create Product Result: " + result);
			System.out.println("DB Path: " + dbPath);
	        
	        return result;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	/*
	 * Updates product in database given a Product
	 * @param product Product with updates
	 * @return True if successful, otherwise False
	 */
	@Override
	public boolean updateProduct(Product product) {
		String sql = "UPDATE product SET name = ?, description = ?, category = ?, price = ?, image_url = ?, quantity = ? WHERE"
				+ "id = ?";
		
		try (
			Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, product.getName());
			statement.setString(2, product.getDescription());
			statement.setString(3, product.getCategory());
			statement.setDouble(4, product.getPrice());
			statement.setString(5, product.getImagePath());
			statement.setInt(6, product.getQuantity());
			statement.setInt(7, product.getId());	// Load attributes
			
			boolean result = statement.execute();
			
			System.out.println("Update Product Result: " + result);
			System.out.println("DB Path: " + dbPath);
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * Delete product from database
	 * @param id The id of the product to remove
	 * @return True if successful, otherwise False
	 */
	@Override
	public boolean deleteProduct(int id) {
		String sql = "DELETE FROM product WHERE id = ?";
		
		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			
			return statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * Update quantity of a product in the database
	 * @param productId Id of the product to update
	 * @param quantity New quantity to set
	 */
	@Override
	public boolean updateInventory(int productId, int quantity) {
		String sql = "UPDATE product SET quantity = ? WHERE id = ?";
		
		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, quantity);
			statement.setInt(2, productId);
			
			return statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * Utility method to create a Product from a query or result set
	 * @param rs Result set to retrieve information from
	 * @return Product filled with result set data
	 */
	private Product mapRowToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setCategory(rs.getString("category"));
        product.setPrice(rs.getDouble("price"));
        product.setImagePath(rs.getString("image_url"));
        product.setQuantity(rs.getInt("quantity"));
        return product;
    }
}