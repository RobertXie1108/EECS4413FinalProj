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
	public List<Product> getProductsByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
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
	public List<Product> searchProducts(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> filterProductsByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> sortProductsBy(String attribute, boolean ascending) {
		// TODO Auto-generated method stub
		return null;
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
		return false;
	}

	@Override
	public boolean deleteProduct(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateInventory(int productId, int quantity) {
		// TODO Auto-generated method stub
		return false;
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
