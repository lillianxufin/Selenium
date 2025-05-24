package CrossBrowsers;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CrossBrowsers {
	private static Logger logger = LogManager.getLogger(CrossBrowsers.class);
    private WebDriver chromeDriver;
    private WebDriver firefoxDriver;
    private WebDriverWait chromeWait;
    private WebDriverWait firefoxWait;

    @BeforeAll
    public void setUp() {
        // Setup ChromeDriver automatically
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
        chromeOptions.addArguments("--disable-extensions");
        chromeDriver = new ChromeDriver(chromeOptions);
        chromeWait = new WebDriverWait(chromeDriver, Duration.ofSeconds(10));

        // Setup FirefoxDriver automatically
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--disable-blink-features=AutomationControlled");
        firefoxDriver = new FirefoxDriver(firefoxOptions);
        firefoxWait = new WebDriverWait(firefoxDriver, Duration.ofSeconds(10));
        
        // Set implicit wait for both drivers
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        firefoxDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        // Maximize windows
        chromeDriver.manage().window().maximize();
        firefoxDriver.manage().window().maximize();
    }

    @Test
    @Order(1)
    public void testCrossBrowserInteraction() {
        // Test on Chrome
        logger.info("Testing on Chrome...");
        chromeDriver.get("https://demoqa.com/text-box");
        WebElement chromeUserName = chromeWait.until(
        	    ExpectedConditions.elementToBeClickable(By.id("userName"))
        	);
        chromeUserName.clear();
        chromeUserName.sendKeys("Hello from Chrome!");
        
        WebElement chromeEmail = chromeDriver.findElement(By.id("userEmail"));
        chromeEmail.clear();
        chromeEmail.sendKeys("chrome@test.com");
        
        // Test on Firefox
        logger.info("Testing on Firefox...");
        firefoxDriver.get("https://demoqa.com/text-box");
        
        WebElement firefoxUserName =firefoxWait.until(
        	    ExpectedConditions.elementToBeClickable(By.id("userName"))
        	);
        firefoxUserName.clear();
        firefoxUserName.sendKeys("Hello from Firefox!");
        
        WebElement firefoxEmail = firefoxDriver.findElement(By.id("userEmail"));
        firefoxEmail.clear();
        firefoxEmail.sendKeys("firefox@test.com");

        // Verify the page title on both browsers
        Assertions.assertEquals("DEMOQA", chromeDriver.getTitle(), "Chrome title mismatch");
        Assertions.assertEquals("DEMOQA", firefoxDriver.getTitle(), "Firefox title mismatch");
        
        // Verify input values
        String chromeInputValue = chromeUserName.getAttribute("value");
        String firefoxInputValue = firefoxUserName.getAttribute("value");
        
        Assertions.assertEquals("Hello from Chrome!", chromeInputValue, "Chrome input value mismatch");
        Assertions.assertEquals("Hello from Firefox!", firefoxInputValue, "Firefox input value mismatch");
        
		logger.info("Test Passed.");
    }
    
    @Test
    @Order(2)
    public void testCrossBrowserNavigation() {
        // Test navigation on both browsers
        logger.info("Testing navigation on both browsers...");
        
        // Chrome navigation test
        chromeDriver.get("https://demoqa.com/elements");
        Assertions.assertTrue(chromeDriver.getCurrentUrl().contains("elements"), 
                            "Chrome navigation failed");
        
        // Firefox navigation test
        firefoxDriver.get("https://demoqa.com/elements");
        Assertions.assertTrue(firefoxDriver.getCurrentUrl().contains("elements"), 
                            "Firefox navigation failed");
        
        logger.info("Navigation test completed on both browsers!");
    }

    @AfterAll
    public void tearDown() {
        // Close both browsers safely
        if (chromeDriver != null) {
            try {
                Thread.sleep(500); // Give browser time to clean up
                chromeDriver.quit();
                logger.info("Chrome browser closed successfully");
            } catch (Exception e) {
                logger.error("Error closing Chrome: " + e.getMessage());
            }
        }
        
        if (firefoxDriver != null) {
            try {
                Thread.sleep(500); // Give browser time to clean up
                firefoxDriver.quit();
                logger.info("Firefox browser closed successfully");
            } catch (Exception e) {
                logger.error("Error closing Firefox: " + e.getMessage());
            }
        }
    }
}