import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Listener.EventHandler;
import io.github.bonigarcia.wdm.WebDriverManager;


public class Hover {
	 private WebDriver driver;
	 private Logger logger = LogManager.getLogger(Hover.class);
	 @BeforeEach
	 public void setup(){
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
	 public void TestDropDownHover() throws InterruptedException {
		 
		driver.get("https://demoqa.com/menu/#");
		Thread.sleep(10000);
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		WebElement mainOption = driver.findElement(By.linkText("Main Item 2"));
		Actions actions = new Actions(driver);
		actions.moveToElement(mainOption).perform();
		wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.linkText("Sub Item"))));
		WebElement subOption1 = driver.findElement(By.xpath("//ul[@id='nav']/li[2]/ul/li/a"));
		WebElement subOption2 = driver.findElement(By.xpath("//ul[@id='nav']/li[2]/ul/li[2]/a"));
		WebElement subOption3 = driver.findElement(By.xpath("//ul[@id='nav']/li[2]/ul/li[3]/a"));
		actions.moveToElement(subOption1).perform();
		Thread.sleep(1000);
		actions.moveToElement(subOption2).perform();
		Thread.sleep(1000);
		actions.moveToElement(subOption3).perform();
		wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.linkText("Sub Sub Item 1"))));
		actions.moveToElement(driver.findElement(By.linkText("Sub Sub Item 1"))).perform();
		Thread.sleep(1000);
		actions.moveToElement(driver.findElement(By.linkText("Sub Sub Item 2"))).perform();
		Thread.sleep(1000);
		logger.info("Test passed.");
	 }
	@Test
	public void TestSideBar() throws InterruptedException {
		 driver.get("https://demoqa.com/slider/");
		    Thread.sleep(1000);
		    
		    WebElement slider = driver.findElement(By.cssSelector("input[type='range']"));
		    Actions actions = new Actions(driver);
		    //Move to value 65 - from center to right
		   actions.moveToElement(slider, 100, 0).click().perform();
		   Assertions.assertEquals(driver.findElement(By.id("sliderValue")).getAttribute("value"), "65");
		    
		   //Move to value 19 - from center to left
		   actions.moveToElement(slider, -200, 0).click().perform();
		   Assertions.assertEquals(driver.findElement(By.id("sliderValue")).getAttribute("value"), "19");
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
