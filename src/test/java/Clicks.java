import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Listener.EventHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ExcelUntils;

/**
 * Test class for click operations using Selenium
 */
public class Clicks {
    
    private WebDriver driver;
    private ExcelUntils excelUntils = new ExcelUntils();
    private Logger logger = LogManager.getLogger(Clicks.class);
    private String testName = this.getClass().getName();
    
    @BeforeEach
    public void setUp() {
        // Set system property to ignore CDP warnings
        System.setProperty("webdriver.chrome.silentOutput", "true");
        
        // Let WebDriverManager find the appropriate driver 
        // for the installed Chrome version
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        // Essential options for compatibility
        options.addArguments("--remote-allow-origins=*");
        // Reduce logging noise
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-logging"});
        // Initialize driver
        driver = new ChromeDriver(options);
        
        EventHandler handler = new EventHandler();
        driver = new EventFiringDecorator<>(handler).decorate(driver);
        
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        
        // Log successful setup
        logger.info("ChromeDriver setup completed successfully");
    }
    
    @Test
    public void testButtonClicks() throws Exception {
        try {
            // Navigate to the test page
            driver.get("https://demoqa.com/buttons");
            
            // Create WebDriverWait with explicit timeout
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            
            // Perform right click
            WebElement rightButton = driver.findElement(By.id("rightClickBtn"));
            Actions actions = new Actions(driver);
            actions.contextClick(rightButton).perform();
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("rightClickMessage"))));
            
            // Perform double click
            WebElement doubleButton = driver.findElement(By.id("doubleClickBtn"));
            Actions actions2 = new Actions(driver);
            actions2.doubleClick(doubleButton).perform();
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("doubleClickMessage"))));
            
            // Perform click
            WebElement clickButton = driver.findElement(By.xpath("//button[text()='Click Me']"));
            clickButton.click();
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("dynamicClickMessage"))));
            
            // Write test result to Excel
            excelUntils.writeTestResult(testName, true);
            logger.info("Test Passed.");
        } catch (TimeoutException e) {
            e.printStackTrace();
            excelUntils.writeTestResult(testName, false);
            logger.info("Test Failed.");
            throw e; // Re-throw the exception to mark the test as failed
        }
    }
    
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            try {
                // Give Chrome a moment to clean up connections
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // Ignore
            }
            driver.quit();
        }
    }
}