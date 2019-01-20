package theStore.models;

public class Products extends BaseModel {
	public Products() throws Exception {
		super();
	}

	public String title = "";
	public int price = 0;
	public int quantity = 0;
	
	@Override
	public String getTable() {
		return "products";
	}
}
