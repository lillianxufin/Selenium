package Junit5;

import java.time.Duration;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DatePickerRepeatedTest {
	private Logger logger = LogManager.getLogger(DatePickerRepeatedTest.class);
	private WebDriver driver;
	private WebDriverWait wait;
	@BeforeEach
	public void beforeEach() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	@RepeatedTest(2)
	public void pickDate() {
		driver.get("https://demoqa.com/date-picker");
		WebElement date = driver.findElement(By.id("datePickerMonthYearInput"));
		date.click();
		WebElement datePicker = driver.findElement(By.cssSelector(".react-datepicker__triangle"));
		wait.until(ExpectedConditions.visibilityOf(datePicker));
		
		WebElement preMonthArrow = driver.findElement(By.cssSelector(".react-datepicker__navigation.react-datepicker__navigation--previous"));
		preMonthArrow.click();
		WebElement firstDay = driver.findElement(By.cssSelector(".react-datepicker__day--001"));
		firstDay.click();
		
		//Valid date
		datePicker = wait.until(ExpectedConditions.elementToBeClickable(By.id("datePickerMonthYearInput")));
		int month = LocalDate.now().getMonthValue()-1;  // Returns 6 for June
		int year = LocalDate.now().getYear();
		String expectedDate = String.format("%02d/01/%d", month, year);
		
		Assertions.assertEquals(expectedDate, datePicker.getAttribute("value"));
	}
	
	@AfterEach
	public void after() {
		if(driver!=null) {
			try {
				driver.quit();
			} catch(Exception e) {
				logger.error("Error: " + e.getMessage());
			}
		}
	}
}
