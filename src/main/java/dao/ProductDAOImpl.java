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
	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<>();
		String sql = "SELECT * FROM product";
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				products.add(mapRowToProduct(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return products;
	}
	
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
                    return mapRowToProduct(rs);
                }
            }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return null;
	}
	
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
	                Product product = mapRowToProduct(rs);
	                result.add(product);
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace(); 
	    }
	    return result;
	}
	
	@Override
	public List<Product> filterProductsByCategory(String category) {
	    List<Product> result = new ArrayList<>();
	    String sql = "SELECT * FROM product WHERE category = ?";
	    
	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {
	        
	        statement.setString(1, category);
	        try (ResultSet rs = statement.executeQuery()) {
	            while (rs.next()) {
	                Product product = mapRowToProduct(rs);
	                result.add(product);
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace(); 
	    }
	    return result;
	}
	
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
	            Product product = mapRowToProduct(rs);
	            result.add(product);
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace(); 
	    }
	    return result;
	}
	
	@Override
	public boolean addProduct(Product product) {
		String sql = "INSERT INTO product (name, description, category, price, image_url, quantity) VALUES (?, ?, ?, ?, ?, ?)";
	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setString(1, product.getName());
	        statement.setString(2, product.getDescription());
	        statement.setString(3, product.getCategory());
	        statement.setDouble(4, product.getPrice());
	        statement.setString(5, product.getImagePath());
	        statement.setInt(6, product.getQuantity());
	        return statement.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public boolean updateProduct(Product product) {
		String sql = "UPDATE product SET name = ?, description = ?, category = ?, price = ?, image_url = ?, quantity = ? WHERE id = ?";
		
		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, product.getName());
			statement.setString(2, product.getDescription());
			statement.setString(3, product.getCategory());
			statement.setDouble(4, product.getPrice());
			statement.setString(5, product.getImagePath());
			statement.setInt(6, product.getQuantity());
			statement.setInt(7, product.getId());
			
			return statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

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