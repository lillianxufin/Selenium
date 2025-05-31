import java.time.Duration;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FunctionTest2 {
	private static WebDriver driver;
	private static Logger logger = LogManager.getLogger(FunctionTest2.class);
    public static void main(String[] args) throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        try {
            driver.get("https://demoqa.com/dynamic-properties");

            FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
            wait.withTimeout(Duration.ofSeconds(10));
            wait.pollingEvery(Duration.ofSeconds(1));
            wait.ignoring(NoSuchElementException.class);

            Function<WebDriver, Boolean> function = (driver) -> {
            	   logger.info("Wait for button color to become red.");
            	   try {
            	       WebElement button = driver.findElement(By.id("colorChange"));
            	       logger.info(button.getCssValue("color"));
            	       String color = button.getCssValue("color");
            	       return color.contains("220, 53, 69, 1)");
            	   } catch (NoSuchElementException e) {
            	       return false;
            	   }
            	};

            // Wait for the condition
            wait.until(function);
            System.out.println("Condition met!");
            
            // Note: switchTo().parentFrame() should only be used if you're currently in a frame
            // driver.switchTo().parentFrame();
            
        } finally {
            driver.quit();
        }
    }
}