package Cache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import Listener.EventHandler;
import Reader.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CacheWithoutPageObject {
    private static Logger logger = LogManager.getLogger(CacheWithoutPageObject.class);
    private static ConfigReader configReader = new ConfigReader();
    
    public static void main(String[] args) throws Exception {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        
        EventHandler handler = new EventHandler();
        driver = new EventFiringDecorator<>(handler).decorate(driver);
        
        // Chrome driver logging setup
        System.setProperty("webdriver.chrome.logfile", configReader.getChromeLogFilePath());
        System.setProperty("webdriver.chrome.verboseLogging", "true");
        
        driver.get("https://demoqa.com/automation-practice-form");
        
        // Method 1: Find element each time (no caching)
        driver.findElement(By.id("firstName")).sendKeys("Virender333");
        
        // Performance test WITHOUT caching - element is located each time
        long withoutCacheStartTime = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++) {
            WebElement firstName = driver.findElement(By.id("firstName"));
            firstName.getText();
        }
        long withoutCacheEndTime = System.currentTimeMillis();
        logger.info("Time taken in seconds Without cache: " + 
                   ((withoutCacheEndTime - withoutCacheStartTime) / 1000.0));
        
        // Method 2: Cache the element reference manually
        WebElement cachedFirstName = driver.findElement(By.id("firstName"));
        
        // Performance test WITH manual caching - element is found once and reused
        long withCacheStartTime = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++) {
            cachedFirstName.getText(); // Reusing the same WebElement reference
        }
        long withCacheEndTime = System.currentTimeMillis();
        logger.info("Time taken in seconds With manual cache: " + 
                   ((withCacheEndTime - withCacheStartTime) / 1000.0));
        
        // Cleanup
        if (driver != null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // Ignore
            }
            driver.quit();
        }
    }
}