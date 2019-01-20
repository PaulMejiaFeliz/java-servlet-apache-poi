package theStore;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import theStore.models.Products;


@WebServlet("/products/")
public class ProductsServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Object handleGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return (new Products()).getAll();
	}

	/**
	 * @throws Exception 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected Object handlePost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Products product = new Products();
		String[] name = request.getParameterValues("name");
		if (name == null) {
			throw new Exception("Name is required");
		}
		
		product.title = name[0];
		
		String[] price = request.getParameterValues("price");
		
		if (price == null) {
			throw new Exception("Price is required");
		}
		
		product.price = Integer.parseInt(price[0]);
		
		String[] quantity = request.getParameterValues("quantity");
		
		if (quantity == null) {
			throw new Exception("Quantity is required");
		}
		
		product.quantity = Integer.parseInt(quantity[0]);
		
		product.save();
		
		return product;
	}

}
