package EventListener;

import java.io.File;
import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Listener.EventHandler;
import Reader.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;

public class EventListenerDemo {
    private static final Logger logger = LogManager.getLogger(EventListenerDemo.class);
    private static ConfigReader configReader = new ConfigReader();
    
    public static void main(String[] args) {
        WebDriver driver = null;
        
        try {
            // Use WebDriverManager instead of system property
            WebDriverManager.chromedriver().setup();
            
            // Setup Chrome options
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            // Add headless option for CI/CD environments if needed
            // options.addArguments("--headless");
            
            // Initialize driver
            driver = new ChromeDriver(options);
            
            // Set implicit wait
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            
            // Setup event handler
            EventHandler handler = new EventHandler();
            driver = new EventFiringDecorator<>(handler).decorate(driver);
            
            // Navigate to test page
            logger.info("Navigating to demo website");
            driver.get("https://demoqa.com/browser-windows");
            
            // Use explicit wait instead of Thread.sleep
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement tabButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("tabButton"))
            );
            
            // Click tab button
            tabButton.click();
            
            // Verify that a new tab was opened
            Assertions.assertEquals(2, driver.getWindowHandles().size());
            logger.info("Successfully opened a new tab");
            
        } catch (Exception e) {
            logger.error("Test failed with exception: " + e.getMessage(), e);
            
            // Take screenshot on failure
            if (driver != null) {
                takeScreenshot(driver, "test_failure");
            }
            
        } finally {
            // Clean up resources
            if (driver != null) {
                logger.info("Closing browser");
                driver.quit();
            }
        }
    }
    
    /**
     * Takes a screenshot and saves it to the specified location
     * 
     * @param driver The WebDriver instance
     * @param screenshotName Name of the screenshot file
     * @return The file path of the saved screenshot
     */
    private static String takeScreenshot(WebDriver driver, String screenshotName) {
        try {
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String filePath = configReader.getScreenshotPath() + screenshotName + "_" + 
                              System.currentTimeMillis() + ".png";
            org.apache.commons.io.FileUtils.copyFile(screenshotFile, new File(filePath));
            logger.info("Screenshot saved at: " + filePath);
            return filePath;
        } catch (Exception e) {
            logger.error("Failed to take screenshot: " + e.getMessage(), e);
            return null;
        }
    }
}
