import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.FluentWait;
import com.google.common.base.Function;

import Listener.EventHandler;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FunctionTest {
	private static Logger logger = LogManager.getLogger(FunctionTest.class);
	private static WebDriver driver;
	public static void main(String[] args) throws Exception {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		EventHandler handler = new EventHandler();
        driver = new EventFiringDecorator<>(handler).decorate(driver);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("https://demoqa.com/buttons/");
		try {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver> (driver);
		wait.withTimeout(Duration.ofSeconds(10));
		wait.pollingEvery(Duration.ofSeconds(1));
		wait.ignoring(NoSuchElementException.class);
		
		driver.findElement(By.xpath("//button[text()='Click Me']")).click();
		wait.until(driver->driver.findElement(By.id("dynamicClickMessage")).getText().equals("You have done a dynamic click"));
		
		Function<WebDriver, WebElement> function = (driver) -> {
		    logger.info("Wait for web element.");
		    return driver.findElement(By.id("test"));
		};
		wait.until(function);
		driver.quit();
		} catch(Exception e) {
			logger.error("Error: " + e.getMessage());
			driver.quit();
		}		
	}	
}
