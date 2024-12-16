package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminPasswordController
 */
@WebServlet("/AdminPasswordController")
public class AdminPasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ADMIN_PASSWORD = "12345678";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminPasswordController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");

        if (ADMIN_PASSWORD.equals(password)) {
            response.sendRedirect("admin.jsp");
        } else {
            // Show error and stay on the password page
            request.setAttribute("errorMessage", "Invalid password. Please try again.");
            request.getRequestDispatcher("adminPassword.jsp").forward(request, response);
        }
    }

}
