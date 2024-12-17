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

import dao.ProductDAOImpl;
import model.Product;

/**
 * Servlet implementation for managing products in the admin dashboard.
 */
@WebServlet("/AdminProductController")
public class AdminProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDAOImpl productDao;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminProductController() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        productDao = new ProductDAOImpl(context);
    }

    /**
     * Handles GET requests.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("edit".equals(action)) {
                // Load product to be edited
                int productId = Integer.parseInt(request.getParameter("id"));
                Product product = productDao.getProductById(productId);
                request.setAttribute("product", product);
                request.getRequestDispatcher("editProduct.jsp").forward(request, response);
            } else {
                // Default: list all products for admin
                List<Product> products = productDao.getAllProducts();
                request.setAttribute("products", products);
                request.getRequestDispatcher("adminProductList.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error processing admin product management request", e);
        }
    }

    /**
     * Handles POST requests for updating products.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("update".equals(action)) {
                // Retrieve product details from the form
                int productId = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                String category = request.getParameter("category");
                double price = Double.parseDouble(request.getParameter("price"));
                String imagePath = request.getParameter("imagePath");
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                // Create Product object
                Product product = new Product();
                product.setId(productId);
                product.setName(name);
                product.setDescription(description);
                product.setCategory(category);
                product.setPrice(price);
                product.setImagePath(imagePath);
                product.setQuantity(quantity);

                // Update the product in the database
                boolean isUpdated = productDao.updateProduct(product);

                if (isUpdated) {
                    request.setAttribute("message", "Product updated successfully!");
                } else {
                    request.setAttribute("errorMessage", "Failed to update product. Please try again.");
                }

                // Redirect back to the product list
                List<Product> products = productDao.getAllProducts();
                request.setAttribute("products", products);
                request.getRequestDispatcher("adminProductList.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error processing product update request", e);
        }
    }
}


