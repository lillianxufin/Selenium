import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Numbers {
	private static WebDriver driver;
	private static WebDriverWait wait;
	public static void main(String[] args0) {
		WebDriverManager.chromedriver().setup();
			try {
				driver = new ChromeDriver();
				wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				
				driver.get("http://demo.guru99.com/test/guru99home/");
				
				List<WebElement> links = driver.findElements(By.xpath("//a"));
				int originalCount = links.size();
				System.out.println("Initial count: " + originalCount);
				scrollUntilCountReached(By.xpath("//a"),originalCount);
				driver.quit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public static void scrollUntilCountReached(By itemLocator, int targetCount) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	    
	    List<WebElement> items;
	    int maxAttempts = 30;
	    int attempts = 0;
	    
	    do {
	        // Scroll down
	        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	        
	        // Wait for new items
	        try {
	            Thread.sleep(1500);
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	        
	        items = driver.findElements(itemLocator);
	        System.out.println("Current count: " + items.size() + ", Target: " + targetCount);
	        
	        attempts++;
	        
	    } while (items.size() < targetCount && attempts < maxAttempts);

	}
}
