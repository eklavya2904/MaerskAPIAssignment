package helpers;

import java.util.Properties;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.testng.ITestResult;

import Utility.reportUtils.ExtentUtility;
import base.SetUp;

public class Reporter extends SetUp {

    private static int testCounter;
    private static boolean isFail = false;
    Properties configProp;
    public static String isLogger;

    public static void initiateTestReport(Class className, String description) {
        try {
            if (!testName.equalsIgnoreCase(className.getSimpleName())) {
                testName = className.getSimpleName();

                extentTest = ExtentUtility.createTestNode(extentReports, testName);
                testCounter = 1;
            }
            extentTestChildNode = ExtentUtility.createTestSuitNode(extentTest, description);
        } catch (Exception e) {
            throw new RuntimeException("error while initiating the test report.", e);
        }
    }

    public static void initiateTestReportbyTestName(String testCaseName, String description) {
        try {
            if (!testName.equalsIgnoreCase(testCaseName)) {
                testName = testCaseName;

                extentTest = ExtentUtility.createTestNode(extentReports, testName);
                testCounter = 1;
            }
            extentTestChildNode = ExtentUtility.createTestSuitNode(extentTest, description);
        } catch (Exception e) {
            throw new RuntimeException("error while initiating the test report.", e);
        }
    }
    
    
    public static void logReport(Class className, String testResult, String logMessage, Throwable throwable) {
        try {
            isLogger = SetUp.isLogger;
            if ("Pass".equals(testResult)) {
                ExtentUtility.logExtent(extentTestChildNode, "Pass", logMessage, null);
                if ("TRUE".equals(isLogger)) {
                    LogManager.getLogger(className).info(logMessage);
                }

            } else if ("Failed".equals(testResult)) {
                ExtentUtility.logExtent(extentTestChildNode, "Failed", logMessage + "<br />" + throwable.getMessage(), null);
                LogManager.getLogger(className).info(logMessage + " : " + throwable.getMessage());
                System.out.println("===========" + logMessage + " : " + throwable.getMessage());
                isFail = true;
            } else if ("Pending".equals(testResult)) {
            ExtentUtility.logExtent(extentTestChildNode, "Pending", logMessage + "<br />" + throwable.getMessage(), null);
            LogManager.getLogger(className).info(logMessage + " : " + throwable.getMessage());
            System.out.println("===========" + logMessage + " : " + throwable.getMessage());
            isFail = true;
        } else if ("Log".equals(testResult)) {
                ExtentUtility.logExtent(extentTestChildNode, "Log", logMessage, null);
                if ("TRUE".equals(isLogger)) {
                    LogManager.getLogger(className).log(Level.INFO, logMessage);
                }
            } else {
                System.out.println("log report option is incorrect.");
            }
        } catch (Exception e) {
            throw new RuntimeException("error while logging the test report.", e);
        }
    }

    public static void tearDownTestReport(ITestResult testResult) {
        try {
            if (testResult.getStatus() == ITestResult.FAILURE && !isFail)
                Reporter.logReport(Reporter.class, log_Type_Fail, "Test Fail", testResult.getThrowable());
            extentTestChildNode = null;
            isFail = false;
        } catch (Exception e) {
            throw new RuntimeException("error while tear down the test report.", e);
        }
    }
}
