package Screenshot;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Listener.EventHandler;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ScreenshotUntils;

public class ScreenshotElement {
	private Logger logger = LogManager.getLogger(ScreenshotElement.class);
	private WebDriver driver;
	private WebDriverWait wait;
	private static ScreenshotUntils screenshot=new ScreenshotUntils();
	 private File[] beforeFiles;
	    private final File screenshotDir = new File("screenshots"); // root-level directory
	@BeforeEach
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		EventHandler handler = new EventHandler();
        driver = new EventFiringDecorator<>(handler).decorate(driver);
		beforeFiles = screenshotDir.listFiles();
        if (beforeFiles == null) beforeFiles = new File[0];
		
	}
	//@Test
	@DisplayName("elementSreenshotTest")
	public void elementSreenshotTest(TestInfo testinfo) {
		 driver.get("http://demo.guru99.com/test/guru99home/");
		 
		 
			    WebElement element = driver.findElement(By.linkText("Linux"));
		        // Try to locate the element
		         // This might not exist
		        wait.until(ExpectedConditions.visibilityOf(element));
		       // Take element screenshot
		        screenshot.captureElementScreenshot(element, testinfo.getDisplayName());
		   
	    File[] afterFiles = screenshotDir.listFiles();
       assertTrue(afterFiles.length > beforeFiles.length,
           "A new screenshot file should have been generated in /screenshot directory");
	}
	//@Test
	@DisplayName("fullscreenSreenshotTest")
	public void fullsreenshotTest(TestInfo testinfo) {
		 driver.get("http://demo.guru99.com/test/guru99home/");
		 
		 try {
			    WebElement element = driver.findElement(By.id("id"));
		        // Try to locate the element
		         // This might not exist
		        wait.until(ExpectedConditions.visibilityOf(element));
		    } catch (Exception e) {
		    	//logger.info("Element not found: " + e.getMessage());
		    }
	    File[] afterFiles = screenshotDir.listFiles();
        assertTrue(afterFiles.length > beforeFiles.length,
            "A new screenshot file should have been generated in /screenshot directory");
	}
	
	@Test
	@DisplayName("iFramescreenshotTest")
	public void iFrameScreenshotTest(TestInfo testinfo) throws IOException {
		driver.get("https://demoqa.com/frames");
		List<WebElement> frameElements = driver.findElements(By.tagName("iframe"));
		logger.info("Frame numbers: " + frameElements.size());
		for (int i = 0; i < frameElements.size(); i++) {
            WebElement frame = frameElements.get(i);
            // Switch to frame
            driver.switchTo().frame(i);
            try {
                WebElement heading = driver.findElement(By.id("sampleHeading"));
                logger.info("Text in frame " + i + ": " + heading.getText());
                File frameScreenshot = frame.getScreenshotAs(OutputType.FILE);
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
                File destFile = new File("screenshots/" + testinfo.getDisplayName() + "-" + timestamp + ".png");
                FileUtils.copyFile(frameScreenshot, destFile);
                File[] afterFiles = screenshotDir.listFiles();
                assertTrue(afterFiles.length > beforeFiles.length,
                    "A new screenshot file should have been generated in /screenshot directory");
            } catch (Exception e) {
                logger.warn("Could not find sampleHeading in frame " + i);
            }
           driver.switchTo().defaultContent();
		}
	}
	
	@AfterEach
	public void after() {
		if(driver!=null) {
			try {
				driver.quit();
			}catch(Exception e) {
				logger.error("Error: " + e.getMessage());
			}
		}
	}
}
