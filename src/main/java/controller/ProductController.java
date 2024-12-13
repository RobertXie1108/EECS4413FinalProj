package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProductDAO;
import dao.ProductDAOImpl;
import dao.UserDAOImpl;
import model.Product;

/**
 * Servlet implementation class ProductController
 */
@WebServlet("/ProductController")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductDAOImpl productDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext context = config.getServletContext();
		productDao = new ProductDAOImpl(context);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String action = request.getParameter("action");

	    try {
	        if ("details".equals(action)) {
	            int productId = Integer.parseInt(request.getParameter("id"));
	            Product product = productDao.getProductById(productId);
	            request.setAttribute("product", product);
	            request.getRequestDispatcher("/WEB-INF/views/productDetails.jsp").forward(request, response);
	        } 
	        else if ("filter".equals(action)) {
	            String category = request.getParameter("category");
	            List<Product> products;
	            
	            if ("All".equalsIgnoreCase(category)) {
	                products = productDao.getAllProducts(); // Fetch all products
	            } else {
	                products = productDao.filterProductsByCategory(category); // Fetch filtered products
	            }
	            
	            request.setAttribute("products", products);
	            request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	        }
	        else {
	            List<Product> products = productDao.getAllProducts();
	            request.setAttribute("products", products);
	            request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	        }
	    } catch (Exception e) {
	        throw new ServletException("Error processing product catalog request", e);
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");

        try {
            if ("addProduct".equals(action)) {
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                String category = request.getParameter("category");
                double price = Double.parseDouble(request.getParameter("price"));
                String imagePath = request.getParameter("imagePath");
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                Product product = new Product();
                product.setName(name);
                product.setDescription(description);
                product.setCategory(category);
                product.setPrice(price);
                product.setImagePath(imagePath);
                product.setQuantity(quantity);

                boolean isAdded = productDao.addProduct(product);

                if (isAdded) {
                    request.setAttribute("message", "Product added successfully!");
                } else {
                    request.setAttribute("message", "Failed to add product. Please try again.");
                }
                request.getRequestDispatcher("addProduct.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        } catch (Exception e) {
            throw new ServletException("Error processing POST request", e);
        }
    }

}
