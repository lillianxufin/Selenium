package main;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Reader.ConfigReader;

public class Hover {
	private static ConfigReader configReader = new ConfigReader();

	public static void main(String[] args) throws Exception {
		System.setProperty("webdriver.chrome.driver", configReader.getChromeDriverPath());
		WebDriver driver = new ChromeDriver();
		try {
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("https://demoqa.com/menu/#");
		Thread.sleep(10000);
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		WebElement mainOption = driver.findElement(By.linkText("Main Item 2"));
		Actions actions = new Actions(driver);
		actions.moveToElement(mainOption).perform();
		wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.linkText("Sub Item"))));
		WebElement subOption1 = driver.findElement(By.xpath("//ul[@id='nav']/li[2]/ul/li/a"));
		WebElement subOption2 = driver.findElement(By.xpath("//ul[@id='nav']/li[2]/ul/li[2]/a"));
		WebElement subOption3 = driver.findElement(By.xpath("//ul[@id='nav']/li[2]/ul/li[3]/a"));
		actions.moveToElement(subOption1).perform();
		Thread.sleep(1000);
		actions.moveToElement(subOption2).perform();
		Thread.sleep(1000);
		actions.moveToElement(subOption3).perform();
		wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.linkText("Sub Sub Item 1"))));
		actions.moveToElement(driver.findElement(By.linkText("Sub Sub Item 1"))).perform();
		Thread.sleep(1000);
		actions.moveToElement(driver.findElement(By.linkText("Sub Sub Item 2"))).perform();
		Thread.sleep(1000);
		
		driver.get("https://demoqa.com/slider/");
		Thread.sleep(10000);
		
		WebElement slider = driver.findElement(By.cssSelector(".range-slider__wrap .range-slider__tooltip.range-slider__tooltip--bottom"));
		Actions actions2 = new Actions(driver);
		actions2.moveToElement(slider, 200, 0).perform();
		Thread.sleep(5000);
		
		driver.switchTo().defaultContent();
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
