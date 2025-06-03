

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ScrollPage {

	public static void main(String[] args) throws Exception {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
		//driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		
		//Launch the application		
        driver.get("http://demo.guru99.com/test/guru99home/");

        //Find element by link text and store in variable "Element"        		
        WebElement Element = driver.findElement(By.linkText("Linux"));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        //This will scroll the page till the element is found		
        js.executeScript("arguments[0].scrollIntoView();", Element);
    	wait.until(ExpectedConditions.visibilityOf(Element));
    	Thread.sleep(5000);
    	WebElement Element2 = driver.findElement(By.linkText("VBScript"));
        //This will scroll the page Horizontally till the element is found		
        js.executeScript("arguments[0].scrollIntoView();", Element2);
        wait.until(ExpectedConditions.visibilityOf(Element2));
        Thread.sleep(5000);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(5000);
        
        js.executeScript("window.scrollTo(0, 0)");
        Thread.sleep(5000);
        System.out.println("Test passed.");
		driver.close();
		driver.quit();
		}
		catch (TimeoutException e) {
			e.printStackTrace();
			System.out.print("Error: "+ e.getMessage());
			driver.close();
		}
	}
	
	
}
