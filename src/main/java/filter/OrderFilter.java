package filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

/**
 * Servlet Filter implementation class OrderFilter
 */
@WebFilter("/OrderController")
public class OrderFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public OrderFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String cardNumber = request.getParameter("ccnumber");
		String cardCode = request.getParameter("cccvv");
		String cardExpiryDate = request.getParameter("ccexpiry");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		
		String[] tempList = {cardNumber, cardCode, cardExpiryDate, email, address};
		String[] fieldNames = {"Credit Card Number", "Security Code", "Expiry Date", "Email", "Address"};
		
		List<String> errorMessages = new ArrayList<>();
		
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("checkout.jsp");
		
		// Check if one of the entries are empty or null
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i] == null || tempList[i].isEmpty()) {
				// Return a message indicating the issue
				errorMessages.add(fieldNames[i] + " cannot be empty!");
			}
		}
		
		// Check if card values are numbers and
		if (!isNumeric(cardNumber) || cardNumber.length() < 13 || cardNumber.length() > 19) {
			errorMessages.add("Invalid credit card input.");
		}
		
		// Check if card security code is 3 digits
		if (cardCode != null && (!isNumeric(cardCode) || cardCode.length() != 3)) {
			errorMessages.add("Security Code must be exactly 3 digits.");
		}
		
		if (cardExpiryDate != null && !cardExpiryDate.matches("\\d{2}/\\d{2}")) {
			errorMessages.add("Expiry date must be in the format 'MM/YY'.");
		}
		
		if (!errorMessages.isEmpty()) {
			request.setAttribute("errorMessages", errorMessages);
			rd = request.getRequestDispatcher("checkout.jsp");
			rd.forward(request, response); // Forward to JSP with error messages
			return; // Stop further processing
		}
		
		// Pass the request along the filter chain
		chain.doFilter(request, response);
	}
	
	public boolean isNumeric(String str) {
	    if (str == null || str.isEmpty()) {
	        return false;
	    }
	    return str.matches("\\d+");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
