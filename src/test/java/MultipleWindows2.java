import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import Listener.EventHandler;
import io.github.bonigarcia.wdm.WebDriverManager;

public class MultipleWindows2 {
	private WebDriver driver;
	private Logger logger = LogManager.getLogger(MultipleWindows2.class);
	@BeforeEach
	public void setup() {
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
	public void newWindow() throws InterruptedException {
		 // Open the main page
        driver.get("https://demoqa.com/browser-windows");
        String mainWindow = driver.getWindowHandle();

        // Open a new window
        WebDriver newWindow = driver.switchTo().newWindow(WindowType.WINDOW);
        newWindow.get("https://demoqa.com/sample"); // or any URL you want to load

        // Log details
        logger.info("New Window URL: " + newWindow.getCurrentUrl());

        // Sleep and close the new window
        Thread.sleep(1000);
        newWindow.close();

        // Switch back to main window
        driver.switchTo().window(mainWindow);
	}
	
	@Test
	public void newTab() throws InterruptedException {
		 driver.get("https://demoqa.com/browser-windows");
	        String mainWindow = driver.getWindowHandle();
	        
	        WebDriver newTab = driver.switchTo().newWindow(WindowType.TAB);
	        newTab.get("https://demoqa.com/sample");
	        
	        logger.info("New Window URL: " + newTab.getCurrentUrl());
	        
	        Thread.sleep(1000);
	        newTab.close();
	        
	        driver.switchTo().window(mainWindow);
	}
	
	
	@AfterEach
	public void after() {
		if(driver!=null) {
			try {
				Thread.sleep(1000);
			}catch (InterruptedException e) {
                // Ignore
            }
			driver.quit();
		}
	}
}
