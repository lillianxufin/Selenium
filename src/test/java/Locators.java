import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Locators {
	private Logger logger = LogManager.getLogger(Locators.class);
	private WebDriver driver;
	private WebDriverWait wait;
	@BeforeEach
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		
	}
	@Test
	public void testAbove() {
		driver.get("https://demoqa.com/selectable");
		WebElement tab = driver.findElement(By.id("demo-tab-grid"));
		tab.click();
		WebElement centerEle = driver.findElement(By.xpath("//div[@id='row2']/li[2]"));
		wait.until(ExpectedConditions.visibilityOf(centerEle));
		WebElement aboveEle = driver.findElement(RelativeLocator.with(By.xpath("//li")).above(centerEle));
		Assertions.assertEquals("Two", aboveEle.getText());
		logger.info("Above passed.");
	}
	@Test
	public void testBelow() {
		driver.get("https://demoqa.com/selectable");
		WebElement tab = driver.findElement(By.id("demo-tab-grid"));
		tab.click();
		WebElement centerEle = driver.findElement(By.xpath("//div[@id='row2']/li[2]"));
		wait.until(ExpectedConditions.visibilityOf(centerEle));
		WebElement aboveEle = driver.findElement(RelativeLocator.with(By.xpath("//li")).below(centerEle));
		Assertions.assertEquals("Eight", aboveEle.getText());
		logger.info("Below passed.");
	}
	@Test
	public void testToLeft() {
		driver.get("https://demoqa.com/selectable");
		WebElement tab = driver.findElement(By.id("demo-tab-grid"));
		tab.click();
		WebElement centerEle = driver.findElement(By.xpath("//div[@id='row2']/li[2]"));
		wait.until(ExpectedConditions.visibilityOf(centerEle));
		WebElement aboveEle = driver.findElement(RelativeLocator.with(By.xpath("//li")).toLeftOf(centerEle));
		Assertions.assertEquals("Four", aboveEle.getText());
		logger.info("To left passed.");
	}
	@Test
	public void testToRight() {
		driver.get("https://demoqa.com/selectable");
		WebElement tab = driver.findElement(By.id("demo-tab-grid"));
		tab.click();
		WebElement centerEle = driver.findElement(By.xpath("//div[@id='row2']/li[2]"));
		wait.until(ExpectedConditions.visibilityOf(centerEle));
		WebElement aboveEle = driver.findElement(RelativeLocator.with(By.xpath("//li")).toRightOf(centerEle));
		Assertions.assertEquals("Six", aboveEle.getText());
		logger.info("To right passed.");
	}
	@Test
	public void testNear() throws InterruptedException {
		driver.get("https://demoqa.com/selectable");
		WebElement tab = driver.findElement(By.id("demo-tab-grid"));
		tab.click();
		WebElement centerEle = driver.findElement(By.xpath("//div[@id='row2']/li[2]"));
		wait.until(ExpectedConditions.visibilityOf(centerEle));
		WebElement nearEle = driver.findElement(RelativeLocator.with(By.xpath("//li")).near(centerEle));
		//Find nearby element
		logger.info(nearEle.getText());
		//Find nearby active element
		WebElement activeEle = driver.findElement(By.xpath("//div[@id='row1']/li[1]"));
		activeEle.click();
	
		WebElement nearEle2 = driver.findElement(RelativeLocator.with(By.cssSelector("li.active")).near(centerEle));
		logger.info(nearEle2.getText());
		Assertions.assertAll(
				()->assertEquals(activeEle.getText(),nearEle2.getText()),
				()->assertNotEquals(nearEle.getText(),nearEle2.getText()));
		logger.info("Near passed.");
	}
	@AfterEach
	public void after() {
		if(driver!=null) {
			try {
				driver.quit();
			}catch(Exception e) {
				logger.error("Error: " + e.getMessage());
			}
		}
	}
}
