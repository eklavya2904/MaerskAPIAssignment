package Utility.fileUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtility {

    public static String takeScreenshot(WebDriver driver, String filePath, String fileName) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            BufferedImage img = ImageIO.read(srcFile);

            String fileFullPath = filePath + fileName + simpleDateFormat.format(new Date()) + ".png";
            //File targetFile = new File(fileFullPath);

            ImageIO.write(img, "png", new File(fileFullPath));
            //FileUtils.copyFile(srcFile, targetFile);

            return fileFullPath;
        } catch (Exception e) {
            throw new RuntimeException(
                    "ScreenshotUtility : take_screenshot || Error while taking the screenshot.\n" + e.getMessage(), e);
        }
    }

    public static String takeBase64Screenshot(WebDriver driver) {
        try {
            return "data:image/png;base64," + ((TakesScreenshot) driver).
                    getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            throw new RuntimeException(
                    "ScreenshotUtility : take_base64Screenshot || Error while taking the base64 screenshot.\n" + e.getMessage(), e);
        }
    }
}
