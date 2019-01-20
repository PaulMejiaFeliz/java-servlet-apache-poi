package theStore;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class TheStoreMain
 */
@WebServlet("/v1")
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String responseData;
		try {
			responseData = gson.toJson(handleGet(request, response));
		} catch (Exception e) {
			throw new ServletException(e.getMessage());
		}
		response.getWriter().append(responseData);
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String responseData;
		try {
			responseData = gson.toJson(handlePost(request, response));
		} catch (Exception e) {
			throw new ServletException(e.getMessage());
		}
		response.getWriter().append(responseData);
	}
    
    protected Object handleGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Map<String, String> data = new HashMap<String, String>();
    	data.put("Name", "The Store Api");
    	data.put("Version", "1.0");
    	return data;
    }
    
    protected Object handlePost(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	throw new ServletException("Route not found");
    }
}
