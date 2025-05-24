import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ExcelUntils;

/**
 * Test class for click operations using Selenium
 */
public class AlertTest {
    
    private WebDriver driver;
    private ExcelUntils excelUntils = new ExcelUntils();
    private Logger logger = LogManager.getLogger(AlertTest.class);
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
        
       // EventHandler handler = new EventHandler();
       // driver = new EventFiringDecorator<>(handler).decorate(driver);
        
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        
        // Log successful setup
        logger.info("ChromeDriver setup completed successfully");
    }
    
    @Test
    public void testButtonClicks() throws Exception {
        try {
        	 driver.get("https://demoqa.com/alerts");
             
             // Print the title of the page
             System.out.println("Title of the page is -> " + driver.getTitle());
             
             // Use explicit wait instead of Thread.sleep
             WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
             wait.until(ExpectedConditions.titleContains("DEMOQA"));
             
             // First alert
             logger.info("Alert 1 test.");
             wait.until(ExpectedConditions.elementToBeClickable(By.id("alertButton")));
             WebElement alertButton = driver.findElement(By.id("alertButton"));
             alertButton.click();
             wait.until(ExpectedConditions.alertIsPresent());
             Alert alert1 = driver.switchTo().alert();
             alert1.accept();
                    
             //Timely Alert
             logger.info("Alert 2 test.");
             driver.findElement(By.id("timerAlertButton")).click();
             wait.until(ExpectedConditions.alertIsPresent());
             Alert alert2 = driver.switchTo().alert();
             alert2.accept();
             
                     
             // Confirm alert
             logger.info("Alert 3 test.");
             driver.findElement(By.id("confirmButton")).click();
             wait.until(ExpectedConditions.alertIsPresent());
             Alert alert3 = driver.switchTo().alert();
             alert3.dismiss();
             wait.until(ExpectedConditions.textToBePresentInElement(
                 driver.findElement(By.id("confirmResult")), "Cancel"));
             wait.until(driver -> 
             driver.findElement(By.id("confirmResult")).getText().equals("You selected Cancel"));

             // Prompt alert
             logger.info("Alert 4 test.");
             driver.findElement(By.id("promtButton")).click();
             wait.until(ExpectedConditions.alertIsPresent());
             Alert alert4 = driver.switchTo().alert();
             String input = "test";
             alert4.sendKeys(input);
             alert4.accept();
             wait.until(ExpectedConditions.textToBePresentInElement(
                 driver.findElement(By.id("promptResult")), input));
            
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