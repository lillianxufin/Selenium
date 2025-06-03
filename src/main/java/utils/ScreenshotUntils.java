package utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ScreenshotUntils {
	 public void capturePageScreenshot(WebDriver driver, String name) {
	        try {
	            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
	            File destFile = new File("screenshots/" + name + "-" + timestamp + ".png");
	            FileUtils.copyFile(screenshot, destFile);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	public void captureElementScreenshot(WebElement element, String name) {
	    try {
	        File screenshot = element.getScreenshotAs(OutputType.FILE);
	        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
	        File destFile = new File("screenshots/" + name + "-" + timestamp + ".png");
	        FileUtils.copyFile(screenshot, destFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
