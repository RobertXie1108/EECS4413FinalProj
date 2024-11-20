package dao;

import java.util.List;
import model.Product;

public interface ProductDAO {
	List<Product> sortByPrice(String s);
	List<Product> sortByName(String s);
	List<Product> findCategory(String s);
	List<Product> findBrand (String s);
	List<Product> findModel (String s);

	List<Product> searchProductByKeyword(String s);
	List<Product> searchProductByType(String s);
	List<Product> searchProductByGenre(String s);
}
