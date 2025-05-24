package Cache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringDecorator;

import Listener.EventHandler;
import Reader.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Cache {
	private static Logger logger = LogManager.getLogger(Cache.class);
	private static ConfigReader configReader = new ConfigReader();
	@FindBy(how = How.ID, using = "firstName")
	public WebElement firsName;

	@FindBy(how = How.ID, using = "firstName")
	@CacheLookup
	public WebElement firsNameCached;

	public static  void main(String[] args) throws Exception {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		WebDriver driver = new ChromeDriver(options);
		EventHandler handler = new EventHandler();
        driver = new EventFiringDecorator<>(handler).decorate(driver);
		// In order to understand how action on WebElements using PageObjects work,
		// we will save all the logs of chrome driver. Below statement helps us
		// save all the logs in a file called TestLog.txt
        System.setProperty("webdriver.chrome.logfile", configReader.getChromeLogFilePath());
      //enables detailed logging
        System.setProperty("webdriver.chrome.verboseLogging", "true");
		
		
		driver.get("https://demoqa.com/automation-practice-form");

		// Initialize the Page object
		Cache pageObject = PageFactory.initElements(driver, Cache.class);

		// Write some values to First and Last Name
		pageObject.firsName.sendKeys("Virender333"); // A FindBy call is triggered to fetch First Name


		// We will first try to get Text from the WebElement version which is not cached.
		// We will measure the time to perform 1000 getText operations
		long withoutCacheStartTime = System.currentTimeMillis();
		for(int i = 0; i < 1000; i ++)
		{
			pageObject.firsName.getText();
		}
		long withoutCacheEndTime = System.currentTimeMillis();
		logger.info("Time take in seconds Without cache: " + ((withoutCacheEndTime - withoutCacheStartTime)/ 1000));

		// Let us now repeat the same process on the cached element and see
		// the amount of time it takes to perform the same operation 1000 times
		long withCacheStartTime = System.currentTimeMillis();
		for(int i = 0; i < 1000; i ++)
		{
			pageObject.firsNameCached.getText();
		}
		long withCacheEndTime = System.currentTimeMillis();
		logger.info("Time take in seconds With cache: " + ((withCacheEndTime - withCacheStartTime)/ 1000));

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
