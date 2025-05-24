import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ExcelUntils;

public class DragAndDrop {
	private static Logger logger = LogManager.getLogger(DragAndDrop.class);
	private static ExcelUntils excelUntils = new ExcelUntils();
	public static void main(String[] args) throws Exception {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		WebDriver driver = new ChromeDriver(options);
		EventHandler handler = new EventHandler();
        driver = new EventFiringDecorator<>(handler).decorate(driver);
		try {
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("https://demoqa.com/droppable/");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		WebElement from = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("draggable")));
		WebElement to = driver.findElement(By.id("droppable"));
		Actions actions = new Actions(driver);
		actions.dragAndDrop(from, to).perform();
		wait.until(ExpectedConditions.textToBe(By.xpath("//div[@id='droppable']//p"), "Dropped!"));
		// Write test result to Excel
        excelUntils.writeTestResult(DragAndDrop.class.getName(), true);
        logger.info("Test Passed.");
		driver.quit();
		}
		catch (TimeoutException e) {
			e.printStackTrace();
			logger.error("Error: " + e.getMessage());
			driver.quit();
		}
	}
	
	
}
