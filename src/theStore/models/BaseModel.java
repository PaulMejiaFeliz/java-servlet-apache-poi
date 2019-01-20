package theStore.models;

import java.util.ArrayList;
import java.util.Map;
import java.lang.reflect.Field;

import theStore.helpers.ExcelDataBaseHelper;

public class BaseModel {
	private transient ExcelDataBaseHelper db = ExcelDataBaseHelper.instance();
	public int id = 0;
	
	public BaseModel() throws Exception {
		db.createModel(this);
	}

	public ArrayList<String> getProperties() {
		ArrayList<String> properties = new ArrayList<String>();
		
		properties.add("id");
		
		for (Field field: getClass().getDeclaredFields()) {			
			properties.add(field.getName());
		}
				
		return properties;
	}
	
	public void save() throws Exception {
		db.saveRecord(this);
	}

	public String getTable() {
		return "Welcome";
	}
	
	public ArrayList<Map<String, String>> getAll() throws Exception {
		return db.fetchRecords(this);
	}
}
