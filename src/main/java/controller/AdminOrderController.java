package controller;

import java.io.IOException;
import java.util.ArrayList;
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
        String action = request.getParameter("action");
        List<Order> orders = new ArrayList<>();

        try {
            if ("filterByCustomer".equals(action)) {
                String username = request.getParameter("customer");
                orders = orderDao.getOrdersByCustomer(username);
            } else if ("filterByProduct".equals(action)) {
                String product = request.getParameter("product");
                orders = orderDao.getOrdersByProduct(product);
            } else if ("filterByDate".equals(action)) {
                String date = request.getParameter("date");
                orders = orderDao.getOrdersByDate(date); 
            } else if ("viewAll".equals(action)) {
                orders = orderDao.getAllOrders();
                request.setAttribute("orders", orders);
            } else {
                orders = orderDao.getAllOrders();
            }

            for (Order order : orders) {
                List<CartItem> items = orderDao.getOrderItems(order.getId());
                order.setItems(items); // Attach items to orders
            }
        } catch (Exception e) {
            throw new ServletException("Error processing sales history", e);
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
