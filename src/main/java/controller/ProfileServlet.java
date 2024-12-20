package controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProductDAOImpl;
import dao.UserDAOImpl;
import model.User;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private UserDAOImpl userDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext context = config.getServletContext();
		userDao = new UserDAOImpl(context);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("user");

        if (loggedInUser == null) {
            // Redirect to login if user is not logged in
            response.sendRedirect("login.jsp");
        } else {
            // Fetch user details from the database using the logged-in user's ID
            User user = userDao.getUserById(loggedInUser.getId());
            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("profile.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
            }
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("user");

        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String fullName = request.getParameter("fullName");
        String shippingAddress = request.getParameter("shippingAddress");
        String creditCardNumber = request.getParameter("creditCardNumber");
        String creditCardExpiry = request.getParameter("creditCardExpiry");
        String creditCardCVV = request.getParameter("creditCardCVV");
        
        String currName = loggedInUser.getFullName();
        String currAddr = loggedInUser.getShippingAddress();
        String currNumber = loggedInUser.getCreditCardNumber();
        String currExpiry = loggedInUser.getCreditCardExpiry();
        String currCVV = loggedInUser.getCreditCardCVV();
        
        // Check if logged in user attributes already has a value (in database) before setting them
        // Also checks if they're changed or not before setting them
        if (null == currName || !currName.equals(fullName)) { loggedInUser.setFullName(fullName); }
        if (null == currAddr || !currAddr.equals(shippingAddress)) { loggedInUser.setShippingAddress(shippingAddress); }
        if (null == currNumber || !currNumber.equals(creditCardNumber)) { loggedInUser.setCreditCardNumber(creditCardNumber); }
        if (null == currExpiry || !currExpiry.equals(creditCardExpiry)) { loggedInUser.setCreditCardExpiry(creditCardExpiry); }
        if (null == currCVV || !currCVV.equals(creditCardCVV)) { loggedInUser.setCreditCardCVV(creditCardCVV); }

        /*// Debugging
        System.out.printf("Full Name: %s\nShipping Address: %s\nCCNumber: %s\nCCExpiry: %s\nCCCVV: %s\n", loggedInUser.getFullName(),
        		loggedInUser.getShippingAddress(), loggedInUser.getCreditCardNumber(),
        		loggedInUser.getCreditCardExpiry(), loggedInUser.getCreditCardCVV());
        */
        
        boolean isUpdated = userDao.updateUser(loggedInUser);

        if (isUpdated) {
            session.setAttribute("user", loggedInUser);
            request.setAttribute("successMessage", "Profile updated successfully!");
        } else {
            request.setAttribute("errorMessage", "Failed to update profile. Please try again.");
        }

        request.setAttribute("user", loggedInUser);
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

}
