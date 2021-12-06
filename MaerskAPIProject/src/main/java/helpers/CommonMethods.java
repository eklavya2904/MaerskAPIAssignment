package helpers;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;
import ComponentFile.ProjectComponent;
import ComponentFile.TokenComponent;
import Utility.fileUtils.PropertiesUtility;
import base.Constants;
import base.SetUp;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
public class CommonMethods extends SetUp {
	CommonMethods commMethod;
	static ProjectComponent pc;
	public static String runTimeFileName = "src//main//resources//Properties//runTimeFile.properties";
	public static void validateResponseStatusCode(Response response, String statusCode) {
		try {
			Assert.assertEquals(response.statusCode() + "", statusCode,
					"Response status code is equal to : " + statusCode);
			Reporter.logReport(CommonMethods.class, Constants.log_Type_Pass,
					"Response code matches with : " + statusCode, null);
		} catch (Exception e) {
			Reporter.logReport(CommonMethods.class, log_Type_Fail,
					"Response code mismatch actual : " + response.statusCode() + " , Expected : " + statusCode, e);
			throw new RuntimeException("error while validating response code.", e);
		}
	}

	public static void validateResponseStatusCoeNegative(Response response, int statusCode) {
		try {
			if(!(response.statusCode()==statusCode)) {
				Reporter.logReport(CommonMethods.class, Constants.log_Type_Pass,
						"Response code matches with : " + statusCode, null);
			}} catch (Exception e) {
				Reporter.logReport(CommonMethods.class, log_Type_Fail,
						"Response code mismatch actual : " + response.statusCode() + " , Expected : " + statusCode, e);
				throw new RuntimeException("error while validating response code.", e);
			}
	}
	public static void validateResponseStatusCodebyMethodname(Response response, String statusCode, String methodName) {
		try {
			Assert.assertEquals(methodName.toUpperCase() + response.statusCode() + "",
					statusCode + "Response status code is equal to : " + statusCode);
			Reporter.logReport(CommonMethods.class, Constants.log_Type_Pass,
					"Response code matches with : " + statusCode, null);
		} catch (Exception e) {
			Reporter.logReport(CommonMethods.class, log_Type_Fail, methodName + "Response code mismatch actual : "
					+ response.statusCode() + " , Expected : " + statusCode, e);
			throw new RuntimeException("error while validating response code.", e);
		}
	}

	public void validateResponseStatusMessage(Response response, String statusValue) {
		try {
			JsonPath jsonPathEvaluator = response.jsonPath();
			String status = jsonPathEvaluator.get("status");
			Assert.assertEquals(status, statusValue, "Response Body Status Message is equal to : " + statusValue);
			Reporter.logReport(CommonMethods.class, Constants.log_Type_Pass,
					"Response Body Status Message matches : " + statusValue, null);
		} catch (Exception e) {
			Reporter.logReport(CommonMethods.class, log_Type_Fail, "Response Body Status Message Mismatch ", e);
			throw new RuntimeException("Response Body Status Message Mismatch .", e);
		}
	}
	// validate response status code and then extract response entity
	public static void extractResponseItem(Response response, String responseItem,String propKey) {
		int statusCode=response.getStatusCode();
		System.out.println(statusCode+" print status code");
		if(statusCode==200) {
			Reporter.logReport(CommonMethods.class, Constants.log_Type_Pass,
					"Response Body Status Message matches : " + statusCode, null);	
		}else if(statusCode==302) {
			String responseVal = response.getHeader(responseItem);
			System.out.println(responseVal+"locationValue");
			PropertiesUtility.setApplicationProperties(CommonMethods.runTimeFileName,propKey, responseVal);
			String authrequired=response.getHeader("X-com-ibm-team-repository-web-auth-msg");
			System.out.println(authrequired+"auth");
			Reporter.logReport(CommonMethods.class, Constants.log_Type_Pass,
					"Response Body Status Message matches : " + statusCode, null);	
		}
	}
	public void handleNullPointer(String str) {
		if (StringUtils.isNotEmpty(str)) {
			System.out.println(str.length());
		} else {
			System.out.println("Empty string");
		}
	}
	// Set values in properties file
	public static void setResponseVal(Response response, String responseJsonPath, String responseVal) {
		JsonPath jsonPathEvaluator = response.jsonPath();
		String value = jsonPathEvaluator.getString(responseJsonPath);
		PropertiesUtility.setApplicationProperties(configFileName, responseVal, value);
	}
	public static int generateRandomInt() {
		int randomNum = ThreadLocalRandom.current().nextInt(9, 999 + 1);
		return randomNum;
	}

	public static String generateRandomValue() {
		String adddValue = "update";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 3) { // length of the random string.
			int index = (int) (rnd.nextFloat() * adddValue.length());
			salt.append(adddValue.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}
	// Code for write to file
	public static BufferedWriter bw;

	public static void writeToFile(String responseVal) throws IOException {
		PrintWriter out = new PrintWriter(
				new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "\\TestData\\response.rdf", false)));
		bw = new BufferedWriter(out);
		bw.write(responseVal);
	}
	// Code for regex(String)
	public static String getStringValue(String mainString, String regexVal, int indexNo) {
		String parentString = mainString;
		String[] splitString = parentString.split(regexVal);
		String finalString = splitString[indexNo];
		return finalString;
	}
	// Extract response value(Response Format is 'JSON')
	public static void responseExtraction(String responseBody, String mainComp, String val1, String val2, String val3,
			String val4, String urlVal) throws JSONException {

		JsonPath jsonPath = new JsonPath(responseBody);
		JSONObject json_obj = null;
		try {
			json_obj = new JSONObject(responseBody);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (json_obj.has(mainComp)) {
			// it's contain value to be read operation
			JSONObject jsonObject = null;
			try {
				jsonObject = json_obj.getJSONObject(mainComp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Iterator<String> keys = jsonObject.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				// System.out.println(key);
				if (key.equalsIgnoreCase(val1) || key.equalsIgnoreCase(val2) || key.equalsIgnoreCase(val3)
						|| key.equalsIgnoreCase(val4)) {
					if (key.equalsIgnoreCase(urlVal)) {
						String postStep2url = getJsonValue(key, jsonObject, "value");
						PropertiesUtility.setApplicationProperties(runTimeFileName, "urlVal", postStep2url);
						System.out.println(postStep2url.toString() + " value of string");
					}
				}
			}
		}
	}
	public static String getJsonValue(String key, JSONObject jsonObject, String value) throws JSONException {
		JSONArray jsonArray = jsonObject.getJSONArray(key);
		String finalval = null;
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject explrObject = jsonArray.getJSONObject(i);
			System.out.println(explrObject.get(value) + " value of json");
			String outhReqTokenVal = explrObject.get(value).toString();
			finalval = outhReqTokenVal;
		}
		return finalval;
	}
}
