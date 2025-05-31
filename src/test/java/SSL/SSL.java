package SSL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import Reader.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SSL {
	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		//Create instance of ChromeOptions Class
		ChromeOptions handlingSSL = new ChromeOptions();

		//Using the accept insecure cert method with true as parameter to accept the untrusted certificate
		handlingSSL.setAcceptInsecureCerts(true);
				
		//Creating instance of Chrome driver by passing reference of ChromeOptions object
		WebDriver driver = new ChromeDriver(handlingSSL);
		
		//Launching the URL
		driver.get("https://expired.badssl.com/");
		System.out.println("The page title is : " + driver.getTitle());
		driver.quit();
		
		// Set up Firefox WebDriver
		WebDriverManager.firefoxdriver().setup();

		// Create instance of FirefoxOptions Class
		FirefoxOptions handlingSSL2 = new FirefoxOptions();

		// Using the accept insecure cert method with true as parameter to accept the untrusted certificate
		handlingSSL.setAcceptInsecureCerts(true);

		// Creating instance of Firefox driver by passing reference of FirefoxOptions object
		WebDriver driver2 = new FirefoxDriver(handlingSSL2);

		// Launching the URL
		driver2.get("https://expired.badssl.com/");
		System.out.println("The page title is : " + driver2.getTitle());
		driver2.quit();
	}

}
