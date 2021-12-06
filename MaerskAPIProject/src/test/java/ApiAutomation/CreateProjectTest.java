package ApiAutomation;

import static PayloadPackage.PayloadClass.createProject;
import java.util.HashMap;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import ComponentFile.ProjectComponent;
import DataProvider.TestDataProvider;
import base.SetUp;
import helpers.CommonMethods;
import helpers.Reporter;
import io.restassured.response.Response;

public class CreateProjectTest extends SetUp {

	ProjectComponent projectComponent;
	CommonMethods commMethod;
	public static String tokenproject;

	Response response = null;
	/*
	 * The createProjectTest method calls get api for openweather website
	 * 
	 */
	@Test(dataProvider = "TestData", dataProviderClass = TestDataProvider.class, groups = {
			"SheetName:ProjectArea", "API", "ProjectAreaCreation", "positive" })
	public void createProjectTest(HashMap<String, String> myData) throws InterruptedException {
		Reporter.initiateTestReportbyTestName((String) myData.get("TestCaseName"),
				String.format((String) myData.get("TestDescription")));
		projectComponent = new ProjectComponent();
		commMethod = new CommonMethods();
			response = projectComponent.CreateProject_Getweather(myData, createProject,
					tokenproject);
			Thread.sleep(5000);
			
			System.out.println("status 46 is" + response.getStatusCode());
			System.out.println("response 47" + response.getBody().asString());
		
		CommonMethods.validateResponseStatusCode(response, responseCode_SuccessLogin);
	}
}