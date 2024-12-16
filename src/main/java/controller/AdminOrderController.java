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

import dao.OrderDAO;
import dao.OrderDAOImpl;
import model.CartItem;
import model.Order;

/**
 * Servlet implementation class AdminOrderController
 */
@WebServlet("/AdminOrderController")
public class AdminOrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderDAO orderDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminOrderController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        orderDao = new OrderDAOImpl(context);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            // Fetch all orders from the database
            List<Order> orders = orderDao.getAllOrders();

            // For each order, fetch and set the items


            // Pass the orders list to the JSP
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("adminOrderHistory.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while fetching order history.");
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
