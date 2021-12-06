package base;

import java.sql.Connection;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import Utility.fileUtils.PropertiesUtility;
import Utility.reportUtils.ExtentUtility;

public class SetUp implements Constants {

    public static Properties properties;
    public static Properties apiProperties;
    public static String testDataSheetPath;
    public static ExtentReports extentReports;
    public static ExtentTest extentTest;
    public static ExtentTest extentTestChildNode;
    public static String testName = "";
    public static Connection dbMerchant;
    public static Connection dbCBS;
    public static Connection dbTransactionService;
    public static String isLogger;
    public static String configFileName = "src//main//resources//Properties//config.properties";


  @BeforeSuite
  public void beforeSetUp() {
        try {
            properties= PropertiesUtility.getApplicationProperties(configFileName);
            
            isLogger = ((String) properties.get("LOGGER")).toUpperCase();
            extentReports = ExtentUtility.createReport("//TestReport//" + properties.getProperty("ExtentTestName"));

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void applyWaitForGetStatus(int time) throws InterruptedException {
        TimeUnit.MINUTES.sleep(time);
    }

    @AfterSuite(groups = {"All"})
    public void cleanUp() {
        try {
            extentReports.flush();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
