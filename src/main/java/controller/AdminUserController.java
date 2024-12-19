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
import dao.UserDAOImpl;
import model.User;

/**
 * Servlet implementation class AdminUserController
 */
@WebServlet("/AdminUserController")
public class AdminUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAOImpl userDao;
       
	public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        userDao = new UserDAOImpl(context);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action = request.getParameter("action");
		
		try {
			if ("edit".equals(action)) {
				int userId = Integer.parseInt(request.getParameter("id"));
				User user = userDao.getUserById(userId);
				request.setAttribute("user", user);
				request.getRequestDispatcher("editUser.jsp").forward(request, response);
			} else {
				List<User> users = userDao.getAllUsers();
				request.setAttribute("users", users);
				request.getRequestDispatcher("adminUserList.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error processing admin user management request", e);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String fullName = request.getParameter("fullName");
		String shippingAddress = request.getParameter("shippingAddress");
		String cardNumber = request.getParameter("cardNumber");
		String cardExpiry = request.getParameter("cardExpiry");
		String cardCVV = request.getParameter("cardCVV");
		
		User u = new User();
		u.setId(id);
		u.setUsername(username);
		u.setPassword(password);
		u.setFullName(fullName);
		u.setShippingAddress(shippingAddress);
		u.setCreditCardNumber(cardNumber);
		u.setCreditCardExpiry(cardExpiry);
		u.setCreditCardCVV(cardCVV);
		
		boolean isUpdated = userDao.updateUser(u);
		
		if (isUpdated) {
			request.setAttribute("message", "User updated successfully!");
		} else {
			request.setAttribute("message", "Failed to update product.");
		}
		
		List<User> users = userDao.getAllUsers();
		request.setAttribute("users", users);
		request.getRequestDispatcher("adminUserList.jsp").forward(request, response);
	}

}
