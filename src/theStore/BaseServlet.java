package theStore;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import theStore.models.BaseModel;

/**
 * Servlet implementation class TheStoreMain
 */
@WebServlet("/api-info")
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();
	protected BaseModel model;
    
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
    	setModel();
    	if (model == null) {
        	Map<String, String> data = new HashMap<String, String>();
        	data.put("Name", "The Store Api");
        	data.put("Version", "1.0");
        	return data;
    	}
    	
		String pathInfo = request.getPathInfo();
		if (pathInfo != null) {
			String[] pathParts = pathInfo.split("/");
			if (pathParts.length == 2) {
				 return model.getById(pathParts[1]);
    		} else {
        		throw new ServletException("Route not found");
    		}
		}
		
		return model.getAll();
    }
    
    protected Object handlePost(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	setModel();
    	if (model == null) {
        	throw new ServletException("Route not found");
    	}

		String pathInfo = request.getPathInfo();
		if (pathInfo != null) {
			String[] pathParts = pathInfo.split("/");
			if (pathParts.length == 2) {
				model.getById(pathParts[1]);
    		} else {
        		throw new ServletException("Route not found");
    		}
		}
		Map<String, String> data = new HashMap<String, String>();
		
		for (Map.Entry<String, String[]> param : request.getParameterMap().entrySet())
		{
			String value = "";
			if (param.getValue() != null && param.getValue().length > 0) {
				value = param.getValue()[0];
			}
			data.put(param.getKey(), value);
		}
		
		if (data.containsKey("id")) {			
			data.remove("id");
		}
		
		model.assign(data);
		model.save();
		
		return model;
    }

	public void setModel() {
		model = null;
	}
}
