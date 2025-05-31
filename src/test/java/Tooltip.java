

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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Tooltip {
	private Logger logger = LogManager.getLogger(Tooltip.class);
	private WebDriver driver;
	private WebDriverWait wait;
	@BeforeEach
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	@Test
	public void tooltip() throws InterruptedException {
		driver.get("https://demoqa.com/tool-tips");
		Actions actions = new Actions(driver);
		WebElement button = driver.findElement(By.id("toolTipButton"));
		actions.moveToElement(button).perform();
		
		Thread.sleep(2000);
		WebElement tooltip = driver.findElement(By.id("buttonToolTip"));
		wait.until(ExpectedConditions.visibilityOf(tooltip));
		Assertions.assertEquals("You hovered over the Button", tooltip.getText());
		logger.info("Test Passed");
	}
	
	@Test
	public void tooltipField() throws InterruptedException {
		driver.get("https://demoqa.com/tool-tips");
		Actions actions = new Actions(driver);
		WebElement button = driver.findElement(By.id("toolTipTextField"));
		actions.moveToElement(button).perform();
		
		Thread.sleep(2000);
		WebElement tooltip = driver.findElement(By.id("textFieldToolTip"));
		wait.until(ExpectedConditions.visibilityOf(tooltip));
		Assertions.assertEquals("You hovered over the text field", tooltip.getText());
		logger.info("Test Passed");
	}
	
	@AfterEach
	public void after() {
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
