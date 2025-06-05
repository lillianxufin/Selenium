package Drivers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import Reader.ConfigReader;


public class headlessChromeDemoTest {
	private static ConfigReader configReader = new ConfigReader();

    public static void main(String[] args) {
        //declare the chrome driver from the local machine location
    	System.setProperty("webdriver.chrome.driver", configReader.getChromeDriverPath());
       
        //create object of chrome options
        ChromeOptions options = new ChromeOptions();
        
        //add the headless argument
        options.addArguments("headless");
        
        //pass the options parameter in the Chrome driver declaration
        WebDriver driver = new ChromeDriver(options);
        
        //Navigate to toolsQA site url
        driver.get("https://demoqa.com/");
        
        //Print the Title of the Page
        System.out.println("Title of the page is -> " + driver.getTitle());
        
        //Close the driver
        driver.close();
    }
}