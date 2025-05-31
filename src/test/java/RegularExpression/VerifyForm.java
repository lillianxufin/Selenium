package RegularExpression;

import java.time.Duration;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class VerifyForm {
	private static Logger logger = LogManager.getLogger(VerifyForm.class);
	public static void main(String[] arg0) {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("https://demoqa.com/automation-practice-form");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		logger.info("Fill in form");
		driver.findElement(By.id("firstName")).sendKeys("John");
		driver.findElement(By.id("lastName")).sendKeys("Doe");
		driver.findElement(By.id("userEmail")).sendKeys("email@example.com");
		driver.findElement(By.xpath("//input[@id='gender-radio-1']/../label")).click();
		driver.findElement(By.id("userNumber")).sendKeys("1111111111");
		JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        logger.info("Submit form.");
		driver.findElement(By.id("submit")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("example-modal-sizes-title-lg"))));
		
	    logger.info("Verify form content");
	    WebElement emailElement = driver.findElement(By.xpath("//div[@class='modal-body']//table/tbody/tr[2]/td[2]"));
	    WebElement dateElement =  driver.findElement(By.xpath("//div[@class='modal-body']//table/tbody/tr[5]/td[2]"));
	    
	    String email = emailElement.getText();
	    String date = dateElement.getText();
	    
	    // Email validation regex
	    String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	    Pattern emailRegex = Pattern.compile(emailPattern);
	    
	    // Date validation regex for dd MMM yyyy format (e.g., "25 Dec 2023")
	    String datePattern = "^\\d{2} [A-Za-z]{3},\\d{4}$";
	    Pattern dateRegex = Pattern.compile(datePattern);
	    
	    // Assertions
	    Assertions.assertTrue(emailRegex.matcher(email).matches(), 
	        "Invalid email format: " + email);
	    
	    Assertions.assertTrue(dateRegex.matcher(date).matches(), 
	        "Invalid date format. Expected dd MMM yyyy, got: " + date);
	    logger.info("Test passed.");
	    driver.quit();
	}
}
