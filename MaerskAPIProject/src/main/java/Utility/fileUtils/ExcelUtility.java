package Utility.fileUtils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExcelUtility {


	public static Map<String, Integer> MapColumnIndex = new HashMap<>();


	public static void getColumnIndex(XSSFSheet sheet) {
		try {
			XSSFRow row = sheet.getRow(0);

			int minColumnIndex = row.getFirstCellNum();
			int maxCoulmnIndex = row.getLastCellNum();

			for (int i = minColumnIndex; i < maxCoulmnIndex; i++) {
				XSSFCell cell = row.getCell(i);
				MapColumnIndex.put(cell.getStringCellValue(), cell.getColumnIndex());
			}
		} catch (Exception e) {
			throw new RuntimeException(
					"ExcelUtility : get_column_index || Error while getting the column index.\n" + e.getMessage(), e);
		}
	}


	public static Map<String, Integer> getExcelColumnIndex(XSSFSheet sheet) {
		Map<String, Integer> MapColumnIndex = new HashMap<>();
		try {
			XSSFRow row = sheet.getRow(0);

			int minColumnIndex = row.getFirstCellNum();
			int maxCoulmnIndex = row.getLastCellNum();

			for (int i = minColumnIndex; i < maxCoulmnIndex; i++) {
				XSSFCell cell = row.getCell(i);
				MapColumnIndex.put(cell.getStringCellValue(), cell.getColumnIndex());
			}
		} catch (Exception e) {
			throw new RuntimeException(
					"ExcelUtility : get_column_index || Error while getting the column index.\n" + e.getMessage(), e);
		}

		return MapColumnIndex;
	}


	public static int getTestCaseCount(XSSFSheet sheet, String testCaseName) {
		DataFormatter dataformatter = new DataFormatter();
		int testCaseCount = 0;

		try {
			int testCaseColumnIndex = MapColumnIndex.get("TestCaseName");
			int executionCheckColumnIndex = MapColumnIndex.get("ExecutionCheck");

			Iterator<Row> iterator = sheet.iterator();
			while (iterator.hasNext()) {
				XSSFRow row = (XSSFRow) iterator.next();
				if (dataformatter.formatCellValue(row.getCell(testCaseColumnIndex)).equalsIgnoreCase(testCaseName)
						&& dataformatter.formatCellValue(row.getCell(executionCheckColumnIndex))
						.equalsIgnoreCase("Y")) {
					testCaseCount++;
				}
			}

			return testCaseCount;
		} catch (Exception e) {
			throw new RuntimeException(
					"ExcelUtility : get_test_case_count || Error while getting the test case count.\n" + e.getMessage(),
					e);
		}
	}


	public static Object[][] getDataFromExcel(String sheetPath, String sheetName, String testcaseName) {

		// to format the data in string
		MapColumnIndex.clear();
		DataFormatter dataformatter = new DataFormatter();

		Object[][] obj;
		File file = new File(sheetPath);
		try (InputStream inStream = new FileInputStream(file); XSSFWorkbook workbook = new XSSFWorkbook(inStream);) {

			XSSFSheet sheet = workbook.getSheet(sheetName);

			// to get the column index
			getColumnIndex(sheet);

			int lastRowNum = sheet.getLastRowNum();
			int lastCellNum = sheet.getRow(0).getLastCellNum();

			// get the count of test cases
			int testCaseCount = getTestCaseCount(sheet, testcaseName);

			obj = new Object[testCaseCount][1];
			int arrIndex = 0;

			for (int i = 0; i < lastRowNum; i++) {

				String testName = sheet.getRow(i + 1).getCell(MapColumnIndex.get("TestCaseName")).getStringCellValue();
				String executionCheck = sheet.getRow(i + 1).getCell(MapColumnIndex.get("ExecutionCheck"))
						.getStringCellValue();

				// getting the values of cell on the basis of test case name and execution check
				if (testName.equalsIgnoreCase(testcaseName) && executionCheck.toUpperCase().equalsIgnoreCase("Y")) {
					Map<Object, Object> map_data = new HashMap<>();
					for (int j = 0; j < lastCellNum; j++) {

						// adding the cell data
						map_data.put(sheet.getRow(0).getCell(j).toString(),
								dataformatter.formatCellValue(sheet.getRow(i + 1).getCell(j)));
					}

					if (arrIndex < testCaseCount) {

						// adding the test data in array
						obj[arrIndex][0] = map_data;
						arrIndex++;
					} else {
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(
					"ExcelUtility : get_data_from_excel || Error while reading the Excel file.\n" + e.getMessage(), e);
		}
		return obj;
	}


	public static XSSFWorkbook readExcelFile(String filePath) {
		File file = new File(filePath);
		XSSFWorkbook workbook;
		try (InputStream inStream = new FileInputStream(file)) {
			workbook = new XSSFWorkbook(inStream);
		} catch (Exception e) {
			throw new RuntimeException(
					"ExcelUtility : get_data_from_excel || Error while reading the Excel file.\n" + e.getMessage(), e);
		}
		return workbook;
	}
}
