package controller;

import model.CartItem;
import model.Product;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProductDAOImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductDAOImpl productDao;
	
    public CartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext context = config.getServletContext();
		productDao = new ProductDAOImpl(context);
	}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            // Retrieve product details from the request
            int productId = Integer.parseInt(request.getParameter("product_id"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            // Get the product from the database
            Product product = productDao.getProductById(productId);

            if (product != null) {
                // Get the cart from the session
                List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
                if (cart == null) {
                    cart = new ArrayList<>();
                }

                // Check if the product already exists in the cart
                boolean itemExists = false;
                for (CartItem item : cart) {
                    if (item.getProduct().getId() == productId) {
                        // Update quantity
                        item.setQuantity(item.getQuantity() + quantity);
                        itemExists = true;
                        break;
                    }
                }

                if (!itemExists) {
                    // Add new item to the cart
                    cart.add(new CartItem(product, quantity));
                }

                // Save the cart back to the session
                session.setAttribute("cart", cart);

                // Redirect to the cart page
                response.sendRedirect("cart.jsp");
            } else {
                // Product not found
                request.setAttribute("errorMessage", "Product not found!");
                request.getRequestDispatcher("home.jsp").forward(request, response);
            }
        }
        
        else if ("remove".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("product_id"));
        	Product product = productDao.getProductById(productId);
            // Get the cart from the session
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            if (cart == null) {
                cart = new ArrayList<>();
            }
            // Loop through and remove instances of the item
            Iterator<CartItem> iterator = cart.iterator();
            while (iterator.hasNext()) {
                CartItem item = iterator.next();
                if (item.getProduct().getId() == productId) {
                    iterator.remove();
                }
            }
            // Save the cart back to the session
            session.setAttribute("cart", cart);
            // Redirect to the cart page
            response.sendRedirect("cart.jsp");
        }
        	
    	else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle displaying the cart
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
}

