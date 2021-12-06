package Utility.reportUtils;

import Utility.fileUtils.ScreenshotUtility;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;

import java.net.InetAddress;

/**
 * call to create a Extent report
 *
 * @author Manisha
 */
public class ExtentUtility {

    public static boolean isSnapShot = true;

    /**
     * method to create the instance of extent report
     *
     * @param extentTestName extent report name
     * @return extent report instance of ExtentReports
     */
    public static ExtentReports createReport(String extentTestName) {
        try {

            ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "//" + extentTestName + ".html");
            ExtentReports extentReports = new ExtentReports();

            extentReports.attachReporter(htmlReporter);
            extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
            extentReports.setSystemInfo("Host Name", InetAddress.getLocalHost().getHostName());
            extentReports.setSystemInfo("OS", System.getProperty("os.name"));

            htmlReporter.config().setDocumentTitle("Extent Report");
            htmlReporter.config().setReportName("Test Report");
            htmlReporter.config().setTheme(Theme.STANDARD);

            return extentReports;

        } catch (Exception e) {
            throw new RuntimeException(
                    "ExtentUtility : create_report || Error while creating the extent report.\n" + e.getMessage(), e);
        }
    }

    /**
     *
     */
    public static ExtentTest createTestNode(ExtentReports extentReports, String testNodeName) {
        try {
            return extentReports.createTest(testNodeName);
        } catch (Exception e) {
            throw new RuntimeException(
                    "ExtentUtility : create_test_node || Error while creating the extent test.\n" + e.getMessage(), e);
        }
    }

    public static ExtentTest createTestSuitNode(ExtentTest extentTest, String suitNodeName) {
        try {
            return extentTest.createNode(suitNodeName);
        } catch (Exception e) {
            throw new RuntimeException(
                    "ExtentUtility : create_test_suit_node || Error while creating the extent suit node.\n" + e.getMessage(), e);
        }
    }

    public static ExtentTest createTestSuitNode(ExtentTest extentTest, String suitNodeName, String testCaseDescription) {
        try {
            return extentTest.createNode(suitNodeName, testCaseDescription);
        } catch (Exception e) {
            throw new RuntimeException(
                    "ExtentUtility : create_test_suit_node || Error while creating the extent suit node.\n" + e.getMessage(), e);
        }
    }

    public static void logExtent(ExtentTest extentTest, String result, String description, WebDriver driver) {
        try {
            switch (result.toUpperCase()) {
                case "PASS":
                    if (isSnapShot && driver != null) {
                        extentTest.pass(description + "<td align=\"right\">", MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtility.takeBase64Screenshot(driver)).build());
                                         
                    } else {
                        extentTest.log(Status.PASS, description + "<td align=\"right\">");
                    }
                    break;
                case "FAIL":
                    if (isSnapShot && driver != null) {
                        extentTest.fail(description + "<td align=\"right\">", MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtility.takeBase64Screenshot(driver)).build());
                    } else {
                        extentTest.log(Status.FAIL, description + "<td align=\"right\">");
                    }
                    break;
                case "LOG":
                    extentTest.log(Status.INFO, description + "<td align=\"right\">");
                    
                    break;
                default:
                    System.out.println("Please pass the appropriate result input. For an example : PASS, FAIL, LOG");
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "ExtentUtility : log_extent || Error while creating the extent suit node.\n" + e.getMessage(), e);
        }
    }
}
