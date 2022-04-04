package com.automationFramework.testUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @author sangale_d
 *
 */
public class Excel_reader {

	public static final Logger logger = Logger.getLogger(Excel_reader.class.getName());

	public final static int RESPONSE_CODE_200 = 200;
	public final static int RESPONSE_CODE_201 = 201;
	public final static int RESPONSE_CODE_400 = 400;
	public final static int RESPONSE_CODE_401 = 401;
	public String UserDetails = "UserDetails";
	public String UserData = "UserData";
	public String RegistrationSheetName = "Registration";
	public String LoginSheetName = "LoginTestData";
	public String ProductSheetName = "productColor";
	public String excellocation = System.getProperty("user.dir") + "/src/main/resources/testData/TestData.xlsx";
	public String apiExcellocation = System.getProperty("user.dir") + "/src/main/resources/testData/APITestData.xlsx";

	FileInputStream file = null;
	XSSFWorkbook workbook = null;
	Object dataSets[][] = null;

	/**
	 * To read excel sheet data
	 * @param excellocation
	 * @param sheetName
	 * @return
	 */
	public Object[][] getExcelData(String excellocation, String sheetName) {
		try {
			logger.info("Creating excel object:-" + excellocation);
			file = new FileInputStream(new File(excellocation));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		// Create Workbook instance holding reference to .xlsx file
		try {
			workbook = new XSSFWorkbook(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Get first/desired sheet from the workbook
		XSSFSheet sheet = workbook.getSheet(sheetName);
		// count number of active tows
		int totalRow = sheet.getLastRowNum();
		// count number of active columns in row
		int totalColumn = sheet.getRow(0).getLastCellNum();
		// Create array of rows and column
		dataSets = new Object[totalRow][totalColumn];
		for (int i = 0; i < totalRow; i++) {
			for (int k = 0; k < totalColumn; k++)
			{
				dataSets[i][k] = sheet.getRow(i + 1).getCell(k).toString();
				XSSFCell cell = sheet.getRow(i + 1).getCell(k);
				// Check the cell type and format accordingly
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					dataSets[i][k] = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_STRING:
					dataSets[i][k] = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					dataSets[i][k] = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_FORMULA:
					dataSets[i][k] = cell.getStringCellValue();
					break;
				}

			}

		}
		return dataSets;

	}

	/**
	 * To Extract response body in file
	 */
	public void storeAPIResponseBody(String responseBody) {

		File fileObj = new File("response.txt");

		try {
			if (fileObj.createNewFile()) {
				FileWriter myWriter = new FileWriter("response.txt");
				myWriter.write(responseBody);
				myWriter.close();
			} else {
				logger.info("Error occured");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
