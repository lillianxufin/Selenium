package AutoIt;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AutoIt {

	private static WebDriver driver;

	public static void main(String[] args) throws IOException, InterruptedException {
		WebDriverManager.chromedriver().setup();
	    driver = new ChromeDriver();

	    driver.manage().window().maximize();
	    driver.get("https://demoqa.com/automation-practice-form");
	    JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,250)");
	    Actions actions = new Actions(driver);
	    actions.click(driver.findElement(By.id("uploadPicture"))).perform();

	   // Runtime.getRuntime().exec("src/test/resources/AutoItScript/UploadFile.exe");
	    try {
	        ProcessBuilder pb = new ProcessBuilder("src/test/resources/AutoItScript/UploadFile.exe");
	        Process process = pb.start();
	        process.waitFor(); // Wait for completion
	    } catch (IOException | InterruptedException e) {
	        e.printStackTrace();
	    }
	    //Thread.sleep(5000);
	    WebElement fileInput = driver.findElement(By.id("uploadPicture"));
	    String fileName = fileInput.getAttribute("value");
	    System.out.println("Selected file: " + fileName);

	    driver.quit();
	    

	}

}