package main;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Reader.ConfigReader;

public class Keyboard {
	private static ConfigReader configReader = new ConfigReader();

	public static void main(String[] args) throws Exception {
		System.setProperty("webdriver.chrome.driver", configReader.getChromeDriverPath());
		WebDriver driver = new ChromeDriver();
		try {
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("https://demoqa.com/text-box");
		Thread.sleep(5000);
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		WebElement fullName = driver.findElement(By.id("userName"));
		fullName.sendKeys("Lillian Xu");
		WebElement email = driver.findElement(By.id("userEmail"));
		email.sendKeys("test@gmail.com");
		WebElement currentAdd = driver.findElement(By.id("currentAddress"));
		currentAdd.sendKeys("123 fea ST");
		currentAdd.sendKeys(Keys.CONTROL);
		currentAdd.sendKeys("A");
		currentAdd.sendKeys(Keys.CONTROL);
		currentAdd.sendKeys("C");
		
		currentAdd.sendKeys(Keys.TAB);
		
		WebElement permAdd = driver.findElement(By.id("permanentAddress"));
		permAdd.sendKeys(Keys.CONTROL);
		permAdd.sendKeys("V");
		
		wait.until(ExpectedConditions.textToBe(By.id("permanentAddress"), currentAdd.getText()));
		
		driver.close();
		driver.quit();
		}
		catch (TimeoutException e) {
			e.printStackTrace();
			System.out.print("test");
			driver.close();
		}
	}
	
	
}
