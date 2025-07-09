package Core;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseTest {

    // ThreadLocal guarantees that each thread gets its own instance of WebDriver
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private DriverFactory driverFactory = new DriverFactory();
    @BeforeEach
    public void setup() {
        // Use WebDriverManager to setup the local chromedriver
        WebDriver driverInstance = driverFactory.createDriver();

        // Optional: Run in headless mode to avoid seeing the UI
        

        // Create a new WebDriver instance and set it for the current thread
        driver.set(driverInstance);
        
        driver.get().manage().window().maximize();
		driver.get().manage().deleteAllCookies();
    }

    @AfterEach
    public void teardown() {
        // Quit the WebDriver instance for the current thread
        if (driver.get() != null) {
            driver.get().quit();
        }
    }

    // A helper method to get the driver for the current thread
    public WebDriver getDriver() {
        return driver.get();
    }
}