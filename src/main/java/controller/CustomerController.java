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

import dao.OrderDAO;
import dao.OrderDAOImpl;
import dao.ProductDAOImpl;
import model.CartItem;
import model.Order;
import model.User;

/**
 * Servlet implementation class CustomerController
 */
@WebServlet("/CustomerController")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderDAO orderDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext context = config.getServletContext();
		orderDAO = new OrderDAOImpl(context);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        switch (action) {
            case "orderHistory":
                showOrderHistory(request, response, user.getId());
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid action");
        }
	}
	
	private void showOrderHistory(HttpServletRequest request, HttpServletResponse response, int userId) throws ServletException, IOException {
        List<Order> orders = orderDAO.getAllOrders();
        
        for (Order order : orders) {
            List<CartItem> items = orderDAO.getOrderItems(order.getId());
            order.setItems(items); // Set items for each order
        }

        request.setAttribute("orders", orders);
        request.getRequestDispatcher("adminOrderHistory.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
