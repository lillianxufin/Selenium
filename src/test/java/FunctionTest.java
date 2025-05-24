package main;


import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

import Reader.ConfigReader;

public class FunctionTest {
	private static ConfigReader configReader = new ConfigReader();

	public static void main(String[] args) throws Exception {
		System.setProperty("webdriver.chrome.driver", configReader.getChromeDriverPath());
		WebDriver driver = new ChromeDriver();
		driver.get("https://demoqa.com/browser-windows/");
		
		FluentWait<WebDriver> wait = new FluentWait<WebDriver> (driver);
		wait.withTimeout(Duration.ofSeconds(10));
		wait.pollingEvery(Duration.ofSeconds(1));
		wait.ignoring(NoSuchElementException.class);
		
		Function<WebDriver, WebElement> function = new Function<WebDriver, WebElement> (){
			public WebElement apply (WebDriver arg0) {
				System.out.println("test");
				WebElement element = arg0.findElement(By.id("test"));
				return element;
			}
		};
		wait.until(function);
	}
	
}
