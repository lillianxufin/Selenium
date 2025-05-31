

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;


public class Keyboard {
	private static Logger logger = LogManager.getLogger(Keyboard.class);

	public static void main(String[] args) throws Exception {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("https://demoqa.com/text-box");
		
		WebElement fullName = driver.findElement(By.id("userName"));
		wait.until(ExpectedConditions.visibilityOf(fullName));
		fullName.sendKeys("John Doe");
		WebElement email = driver.findElement(By.id("userEmail"));
		email.sendKeys("test@gmail.com");
		WebElement currentAdd = driver.findElement(By.id("currentAddress"));
		currentAdd.sendKeys("123 fea ST");
		currentAdd.sendKeys(Keys.chord(Keys.CONTROL, "A"));
		currentAdd.sendKeys(Keys.chord(Keys.CONTROL, "C"));
		currentAdd.sendKeys(Keys.TAB);
		Thread.sleep(1000);
		
		// Paste into permanent address
		WebElement permAdd = driver.findElement(By.id("permanentAddress"));
		permAdd.sendKeys(Keys.chord(Keys.CONTROL, "v")); // Ctrl+V
		wait.until(ExpectedConditions.textToBe(By.id("permanentAddress"), currentAdd.getText()));
		Thread.sleep(1000);
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
