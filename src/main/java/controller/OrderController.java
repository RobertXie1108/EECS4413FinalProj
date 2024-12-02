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
import javax.servlet.http.HttpSession;

import dao.OrderDAOImpl;
import dao.ProductDAOImpl;
import model.CartItem;
import model.Order;
import model.User;

/**
 * Servlet implementation class OrderController
 */
@WebServlet("/OrderController")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderDAOImpl orderDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext context = config.getServletContext();
		orderDao = new OrderDAOImpl(context);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

	    if (cart == null || cart.isEmpty()) {
	        request.setAttribute("message", "Your cart is empty!");
	        request.getRequestDispatcher("cart.jsp").forward(request, response);
	        return;
	    }

	    try {
	        // Retrieve the User object from session
	        User user = (User) session.getAttribute("user");

	        if (user == null) {
	            // If the user is not logged in, redirect to login page
	            response.sendRedirect("login.jsp");
	            return;
	        }

	        // Now get the user_id from the user object
	        int userId = user.getId();  // Assuming you have a getId() method in the User class

	        // Create and save the order
	        Order order = new Order();
	        order.setUserId(userId);  // Set the user_id
	        order.setItems(cart);     // Set the cart items

	        // Save the order
	        boolean isOrderSaved = orderDao.placeOrder(order);

	        if (isOrderSaved) {
	            session.removeAttribute("cart"); // Clear the cart after successful order
	            request.setAttribute("message", "Checkout successful! Your order has been placed.");
	            request.getRequestDispatcher("orderSuccess.jsp").forward(request, response);
	        } else {
	            request.setAttribute("message", "Checkout failed. Please try again.");
	            request.getRequestDispatcher("cart.jsp").forward(request, response);
	        }
	    } catch (Exception e) {
	        throw new ServletException("Error processing checkout", e);
	    }
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
