package theStore;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ErrorHandlerServlet
 */
@WebServlet("/error")
public class ErrorHandlerServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
	protected Object handleGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> data = new HashMap<String, String>();
		response.setStatus(400);
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        
        try {
        	data.put("Error", exception.getMessage());
        } catch (NullPointerException ex) {
            data.put("Error", "Route not found");
        }
		
    	return data;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
