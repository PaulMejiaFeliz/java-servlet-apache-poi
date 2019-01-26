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

	public BaseModel getById(String id) throws Exception {
		int rowId = Integer.parseInt(id);
		return db.fetchRecord(this, rowId);
	}

	public void assign(Map<String, String> record) {
		if (record.containsKey("id")) {			
			id = Integer.parseInt(record.get("id"));
		}

		for (Field field: getClass().getDeclaredFields()) {
			if (!record.containsKey(field.getName())) {
				continue;
			}
			
			try {
				switch (field.getType().getName()) {
					case "java.lang.String":
						field.set(this, record.get(field.getName()));
						break;
					case "int":
						field.setInt(this, Integer.parseInt(record.get(field.getName())));
						break;
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
