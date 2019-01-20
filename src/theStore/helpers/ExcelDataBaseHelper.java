package theStore.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import theStore.models.BaseModel;

public class ExcelDataBaseHelper {
	private static final String bdPath = "C:\\Users\\paulm\\eclipse-workspace\\TheStore\\WebContent\\WEB-INF\\data\\db.xlsx";
	private File dbFile;
	private XSSFWorkbook workbook;
	
	private static ExcelDataBaseHelper instance = null;

	private ExcelDataBaseHelper() throws InvalidFormatException, IOException, Exception {
		dbFile = new File(bdPath);
		
		if (dbFile.exists()) {
			workbook = new XSSFWorkbook(new FileInputStream(dbFile));
		} else {
			dbFile.createNewFile();
			workbook = new XSSFWorkbook();
		}
	}
	
	public static ExcelDataBaseHelper instance() throws Exception {
		if (instance == null) {
			instance  = new ExcelDataBaseHelper();
		}
	
		return instance;
	}

	public void createModel(BaseModel model) throws Exception {
		String table = model.getTable();
		XSSFSheet sheet = workbook.getSheet(table);
		if (sheet == null) {
			sheet = workbook.createSheet(table);
		}
		
		XSSFRow header = sheet.getRow(0);
		if (header == null) {
			header = sheet.createRow(0);
		}
		
		ArrayList<String> properties = model.getProperties();
		
		Iterator<Cell> cells = header.cellIterator();
		while (cells.hasNext()) {
			Cell cell = cells.next();
			properties.remove(cell.getStringCellValue());
		}
				
		for (String property: properties) {
			short lastIndex = header.getLastCellNum() == -1 ? 0 : header.getLastCellNum();
			XSSFCell cell = header.createCell(lastIndex);
			cell.setCellValue(property);
		}
		
		workbook.write(new FileOutputStream(dbFile));
	}
	
	public void saveRecord(BaseModel model) throws Exception {
		XSSFSheet sheet = workbook.getSheet(model.getTable());

		XSSFRow header = sheet.getRow(0);

		Map<String, Integer> columnsIndex = new HashMap<String, Integer>();
		Iterator<Cell> cells = header.cellIterator();
		int cellIndex = 0;
		while (cells.hasNext()) {
			Cell cell = cells.next();
			columnsIndex.put(cell.getStringCellValue(), cellIndex++);
		}
				
		XSSFRow row;
		if (model.id <= 0) {
			int index = sheet.getLastRowNum() + 1;
			row = sheet.createRow(index);
			model.id = index;
		} else {
			row = sheet.getRow(model.id);
		}
		
		JsonObject data = (new Gson()).toJsonTree(model).getAsJsonObject();

		for (String property: model.getProperties()) {
			XSSFCell cell = row.createCell(columnsIndex.get(property));
			cell.setCellValue(data.get(property).getAsString());
		}
		
		workbook.write(new FileOutputStream(dbFile));
	}
	
	public ArrayList<Map<String, String>> fetchRecords(BaseModel model) throws Exception {
		ArrayList<Map<String, String>> records = new ArrayList<Map<String, String>>();

		XSSFSheet sheet = workbook.getSheet(model.getTable());

		XSSFRow header = sheet.getRow(0);
		
		Map<String, Integer> columnsIndex = new HashMap<String, Integer>();
		Iterator<Cell> cells = header.cellIterator();
		int cellIndex = 0;
		while (cells.hasNext()) {
			Cell cell = cells.next();
			columnsIndex.put(cell.getStringCellValue(), cellIndex++);
		}
		
		Iterator<Row> rows = sheet.rowIterator();
		rows.next();
		
		while(rows.hasNext()) {
			Row row = rows.next();
			Map<String, String> record = new HashMap<String, String>();
			for (String property: model.getProperties()) {
				Cell cell = row.getCell(columnsIndex.get(property));
	            record.put(property, cell.getRichStringCellValue().getString());
			}
			
			records.add(record);
		}
		
		return records;
	}
}
