package Core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class DriverFactory {

    public WebDriver createDriver() {
    	WebDriver driver;
        // Read the browser property from command line, default to CHROME
        String browser = System.getProperty("browser", "CHROME").toUpperCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        switch (browser) {
            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "EDGE":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless");
                }
                driver = new EdgeDriver(edgeOptions);
                break;
            case "CHROME":
            default:
            	WebDriverManager.chromedriver().setup();
                // Create Chrome-specific options
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless");
                }
                // Pass the options into the driver constructor
                driver = new ChromeDriver(chromeOptions);
                break;
        }
        return driver;
    }
}