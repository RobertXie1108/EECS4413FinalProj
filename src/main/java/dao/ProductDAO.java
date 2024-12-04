package dao;

import java.util.List;

import model.Product;

public interface ProductDAO {
    List<Product> getAllProducts();
    Product getProductById(int id);
    List<Product> searchProducts(String keyword);
    List<Product> filterProductsByCategory(String category);
    List<Product> sortProductsBy(String attribute, boolean ascending);
    boolean addProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(int id);
    boolean updateInventory(int productId, int quantity); 
}

