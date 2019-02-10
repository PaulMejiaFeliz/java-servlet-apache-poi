package theStore.models;

import java.util.Date;

public class Products extends BaseModel {
	public Products() throws Exception {
            super();
	}

	public String title = "";
	public int price = 0;
	public int quantity = 0;
        public Date date = new Date();
	
	@Override
	public String getTable() {
            return "products";
	}
}
