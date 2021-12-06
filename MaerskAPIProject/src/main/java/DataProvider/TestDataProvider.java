package DataProvider;

import Utility.fileUtils.ExcelUtility;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestDataProvider {
	@DataProvider(name = "TestData")
	public static Object[][] getDataFromDataprovider(Method m) throws IOException {
		String tabName = getSheetName(m);
		Object[][] excelData = null;
		if (tabName != "") {
			String testDataPath = System.getProperty("user.dir") + File.separator + "TestData" + File.separator + "MaerskTestDemo.xlsx";
			excelData = ExcelUtility.getDataFromExcel(testDataPath, tabName, m.getName());
		}
		ArrayList browsersNames = getBrowserName(m);
		Object[][] objTemp = returnDataProvider(browsersNames, excelData);
		return objTemp;
	}

	private static Object[][] returnDataProvider(ArrayList browsersNames, Object[][] excelData) {

		//api call , browsername should be blank
		if (browsersNames.size() <= 0 && excelData != null) {
			int startSize = excelData.length;
			Object[][] objTemp = new Object[startSize][1];
			for (int testDataInc = 0; testDataInc < excelData.length; testDataInc++) {
				objTemp[testDataInc][0] = excelData[testDataInc][0];
			}
			return objTemp;
		} else if (excelData == null && browsersNames.size() > 0)// without excel data UI
		{
			int startSize = browsersNames.size();
			Object[][] objTemp = new Object[startSize][1];
			for (int browserInc = 0; browserInc < browsersNames.size(); browserInc++) {
				objTemp[browserInc][0] = browsersNames.get(browserInc);
			}
			return objTemp;
		} else { // both values are present UI

			// with excel data
			int startSize = browsersNames.size() * excelData.length;
			Object[][] objTemp = new Object[startSize][2];

			for (int browserInc = 0; browserInc < browsersNames.size(); browserInc++) {
				for (int testDataInc = 0; testDataInc < excelData.length; testDataInc++) {
					objTemp[testDataInc + (browserInc * excelData.length)][0] = browsersNames.get(browserInc);
					objTemp[testDataInc + (browserInc * excelData.length)][1] = excelData[testDataInc][0];
				}
			}
			return objTemp;
		}
	}

	private static ArrayList getBrowserName(Method m) {
		ArrayList objTemp = new ArrayList();
		Annotation anno = m.getAnnotation(Test.class);
		String strTemp = anno.toString();
		strTemp = strTemp.replaceAll(" ", "");
		try {
			strTemp = strTemp.substring(strTemp.indexOf("groups=[") + 8, strTemp.indexOf("]", strTemp.indexOf("groups=[") + 8));
		} catch (Exception e) {
			strTemp = strTemp.substring(strTemp.indexOf("groups={") + 8, strTemp.indexOf("}", strTemp.indexOf("groups={") + 8));
		}
		if (strTemp.contains("BrowserList")) {
			String[] arrTemp = strTemp.split(",");

			for (int arrTempCounter = 0; arrTempCounter < arrTemp.length; arrTempCounter++) {
				if (arrTemp[arrTempCounter].contains("BrowserList")) {
					strTemp = arrTemp[arrTempCounter];
					strTemp = strTemp.replaceAll("\"", "");
					break;
				}
			}
			arrTemp = strTemp.split(":");
			for (int arrTempCounter = 1; arrTempCounter < arrTemp.length; arrTempCounter++) {
				objTemp.add(arrTemp[arrTempCounter].toUpperCase());
			}

			return objTemp;
		} else {
			return objTemp;
		}

	}

	private static String getSheetName(Method m) {
		String sheetName = "";
		Annotation anno = m.getAnnotation(Test.class);
		String strTemp = anno.toString();

		strTemp = strTemp.replaceAll(" ", "");
		try {
			strTemp = strTemp.substring(strTemp.indexOf("groups=[") + 8, strTemp.indexOf("]", strTemp.indexOf("groups=[") + 8));
		} catch (Exception e) {
			strTemp = strTemp.substring(strTemp.indexOf("groups={") + 8, strTemp.indexOf("}", strTemp.indexOf("groups={") + 8));
		}

		if (strTemp.contains("SheetName")) {
			String[] arrTemp = strTemp.split(",");
			for (int arrTempCounter = 0; arrTempCounter < arrTemp.length; arrTempCounter++) {
				if (arrTemp[arrTempCounter].contains("SheetName")) {
					strTemp = arrTemp[arrTempCounter];
					strTemp = strTemp.replaceAll("\"", "");
					break;
				}
			}
			arrTemp = strTemp.split(":");
			sheetName = arrTemp[1];
		}
		return sheetName;
	}
}
