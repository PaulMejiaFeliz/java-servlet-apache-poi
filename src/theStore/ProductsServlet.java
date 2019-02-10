package theStore;

import javax.servlet.annotation.WebServlet;

import theStore.models.Products;


@WebServlet("/products/*")
public class ProductsServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void setModel() {
        try {
            super.model = new Products();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
