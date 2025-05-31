import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

import Listener.EventHandler;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Radio {
	private static Logger logger = LogManager.getLogger(Radio.class);
	public static void main(String[] args) throws Exception {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		EventHandler handler = new EventHandler();
        driver = new EventFiringDecorator<>(handler).decorate(driver);
		try {
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("https://demoqa.com/radio-button");
		WebElement radioEleWrapper = driver.findElement(By.id("yesRadio"));
		WebElement radioEle = driver.findElement(By.xpath("//input[@id='yesRadio']//..//label"));
		boolean selectEle = radioEleWrapper.isEnabled()&&!radioEleWrapper.isSelected();
		// performing click operation if element is not already selected
		if (selectEle) {
			logger.info("Click Yes Radio.");
			radioEle.click();
			Assertions.assertTrue(radioEleWrapper.isSelected());
		}

		/**
		 * Find radio button using Xpath, Validate isDisplayed and click to select
		 */
		WebElement radioEle2Wrapper = driver.findElement(By.id("impressiveRadio"));
		WebElement radioEle2 = driver.findElement(By.xpath("//input[@id='impressiveRadio']//..//label"));
		boolean selectEle2 = radioEle2Wrapper.isEnabled()&&!radioEle2Wrapper.isSelected();

		// performing click operation if element is displayed
		if (selectEle2) {
			logger.info("Click Impressive Radio.");
			radioEle2.click();
			Assertions.assertTrue(radioEle2Wrapper.isSelected());
		}

		/**
		 * Find radio button using CSS Selector, Validate isEnabled and click to select
		 */
		WebElement radioNoWrapper = driver.findElement(By.id("noRadio")); // This should select the <input>
		WebElement radioNo = driver.findElement(By.xpath("//input[@id='noRadio']//..//label"));
		boolean selectNo = radioNoWrapper.isEnabled()&&!radioNoWrapper.isSelected();
		// performing click operation if element is enabled
		if (selectNo) {
			radioNo.click();
			logger.info("Click No Radio");
		} else {
			logger.info("Radio disabled");
			Assertions.assertFalse(radioNoWrapper.isSelected());
		}
		driver.close();
		driver.quit();
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
			driver.close();
			driver.quit();
		}
	}
	
	
}
