package filter;

import java.io.IOException;
import java.io.PrintWriter;

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
@WebFilter("/OrderServlet")
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
		// Load parameters (payment and shipping information)
		String cardNumber = request.getParameter("cardNumber");
		String cardCode = request.getParameter("cardCode");
		String cardExpiryDate = request.getParameter("cardExpiryDate");
		String customerName = request.getParameter("customerName");
		String customerPhoneNumber = request.getParameter("customerPhoneNumber");
		String customerCity = request.getParameter("customerCity");
		String customerCountry = request.getParameter("customerCountry");
		
		String[] tempList = {cardNumber, cardCode, cardExpiryDate, customerName, 
								customerPhoneNumber, customerCity, customerCountry};
		
		boolean hasError = false;
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("checkout.jsp");
		
		// Check if one of the entries are empty or null
		for (String s : tempList) {
			if (s == null || s.isEmpty()) {
				// Return a message indicating the issue
				out.print("<span style='color: red;'>One or more fields cannot be empty!</span><br>");
				hasError = true;
				break;
			}
		}
		
		// Check if card values are numbers
		if (!isNumeric(cardNumber) || !isNumeric(cardCode) || !isNumeric(cardExpiryDate)) {
			out.print("<span style='color: red;'>Invalid credit card information; Fields should be numbers with no spaces!</span><br>");
			hasError = true;
		}
		
		// Check if card security code is 3 digits
		if (cardCode.length() != 3) {
			out.print("<span style='color: red;'>Security Code must be 3 digits!</span><br>");
			hasError = true;
		}
		
		// Check if expiry date is 4 digits
		if (cardExpiryDate.length() != 4) {
			out.print("<span style='color: red;'>Expiry date must be of form: 'mmyy'</span><br>");
			hasError = true;
		}
		if (hasError) {
			rd.include(request, response);
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
