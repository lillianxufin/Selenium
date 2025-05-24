package main;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Reader.ConfigReader;

public class MultipleWindows {
	private static ConfigReader configReader = new ConfigReader();

	public static void main(String[] args) throws Exception {
		System.setProperty("webdriver.chrome.driver", configReader.getChromeDriverPath());
		WebDriver driver = new ChromeDriver();
		try {
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("https://demoqa.com/browser-windows");
		
		driver.findElement(By.id("windowButton")).click();
		driver.findElement(By.id("messageWindowButton")).click();
		
		
		Set<String> windowHandlers = driver.getWindowHandles();
		
		String mainWindow = driver.getWindowHandle();
		Iterator<String> iterator = windowHandlers.iterator();
		
		while(iterator.hasNext()) {
			String childWindow = iterator.next();
			if(!childWindow.equals(mainWindow)) {
				System.out.println(childWindow.toString());
				driver.switchTo().window(childWindow);
				Thread.sleep(1000);
				driver.close();
				driver.switchTo().window(mainWindow);
			}
		}
			
			driver.switchTo().window(mainWindow);
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
