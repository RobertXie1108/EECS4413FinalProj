package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;

import model.Product;

public class ProductDAOImpl implements ProductDAO {
	private String dbPath;
	
	public ProductDAOImpl(ServletContext servletContext) {
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
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getProductById(int id) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateProduct(Product product) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteProduct(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reduceInventory(int productId, int quantity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateInventory(int productId, int quantity) {
		// TODO Auto-generated method stub
		return false;
	}

}
