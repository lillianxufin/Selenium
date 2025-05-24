
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Frames {
	private static Logger logger = LogManager.getLogger(Frames.class);
	public static void main(String[] args) throws Exception {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		try {
		driver.get("https://demoqa.com/frames");

		
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		Integer numberOfFrames = Integer.parseInt(exe.executeScript("return window.length").toString());
		Long numberOfFrames2 =  (Long) exe.executeScript("return window.length");
		logger.info(numberOfFrames);
		logger.info(numberOfFrames2);
		
		List<WebElement> frameElements = driver.findElements(By.tagName("iframe"));
		int number = frameElements.size();
		logger.info(frameElements.size());
		logger.info(number);
		for (int i = 0; i < frameElements.size(); i++) {
            WebElement frame = frameElements.get(i);
            logger.info("Switching to frame index: " + i);

            // Switch to frame
            driver.switchTo().frame(frame);

            // Example action inside the frame
            try {
                WebElement heading = driver.findElement(By.id("sampleHeading"));
                logger.info("Text in frame " + i + ": " + heading.getText());
            } catch (Exception e) {
                logger.warn("Could not find sampleHeading in frame " + i);
            }

            // Switch back to main document
            driver.switchTo().defaultContent();
            logger.info("Switched back to main document.");
        }
		driver.quit();
		}
		catch (TimeoutException e) {
			e.printStackTrace();
			logger.error("Error Message: " + e.getMessage());
			driver.quit();
		}
	}
	
	
}
