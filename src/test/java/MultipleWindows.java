import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import Listener.EventHandler;
import io.github.bonigarcia.wdm.WebDriverManager;

public class MultipleWindows {
	private WebDriver driver;
	private Logger logger = LogManager.getLogger(MultipleWindows.class);
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
		driver.get("https://demoqa.com/browser-windows");
		
		driver.findElement(By.id("windowButton")).click();
        Set<String> windowHandlers = driver.getWindowHandles();
		
		String mainWindow = driver.getWindowHandle();
		Iterator<String> iterator = windowHandlers.iterator();
		
		while(iterator.hasNext()) {
			String childWindow = iterator.next();
			if(!childWindow.equals(mainWindow)) {
				logger.info(childWindow.toString());
				
				driver.switchTo().window(childWindow);
				logger.info("New Window URL: " + driver.getCurrentUrl());
				Thread.sleep(1000);
				driver.close();
				driver.switchTo().window(mainWindow);
			}
		}
			
			driver.switchTo().window(mainWindow);
	}
	@Test
	public void newWindowWithMessage() throws InterruptedException {
		driver.get("https://demoqa.com/browser-windows");
		
		driver.findElement(By.id("messageWindowButton")).click();
        Set<String> windowHandlers = driver.getWindowHandles();
		
		String mainWindow = driver.getWindowHandle();
		Iterator<String> iterator = windowHandlers.iterator();
		
		while(iterator.hasNext()) {
			String childWindow = iterator.next();
			if(!childWindow.equals(mainWindow)) {
				logger.info(childWindow.toString());
				driver.switchTo().window(childWindow);
				//Assertions.assertEquals(driver.getTitle(), "abount:blank");
				Thread.sleep(1000);
				driver.close();
				driver.switchTo().window(mainWindow);
			}
		}
			
			driver.switchTo().window(mainWindow);
	}
	@Test
	public void newTab() throws InterruptedException {
		driver.get("https://demoqa.com/browser-windows");
		
		driver.findElement(By.id("tabButton")).click();
        Set<String> windowHandlers = driver.getWindowHandles();
		
		String mainWindow = driver.getWindowHandle();
		Iterator<String> iterator = windowHandlers.iterator();
		
		while(iterator.hasNext()) {
			String childWindow = iterator.next();
			if(!childWindow.equals(mainWindow)) {
				logger.info(childWindow.toString());
				driver.switchTo().window(childWindow);
				Assertions.assertEquals(driver.getCurrentUrl(), "https://demoqa.com/sample");
				Thread.sleep(1000);
				driver.close();
				driver.switchTo().window(mainWindow);
			}
		}
			
			driver.switchTo().window(mainWindow);
	}
	@Test
	public void swithBetweenWindows() throws InterruptedException {
		driver.get("https://demoqa.com/browser-windows");
		
		driver.findElement(By.id("windowButton")).click();
		driver.findElement(By.id("messageWindowButton")).click();
		driver.findElement(By.id("tabButton")).click();
		
		Set<String> windowHandlers = driver.getWindowHandles();
		
		String mainWindow = driver.getWindowHandle();
		Iterator<String> iterator = windowHandlers.iterator();
		
		while(iterator.hasNext()) {
			String childWindow = iterator.next();
			if(!childWindow.equals(mainWindow)) {
				logger.info(childWindow.toString());
				driver.switchTo().window(childWindow);
				Thread.sleep(1000);
				driver.close();
				driver.switchTo().window(mainWindow);
			}
		}	
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
