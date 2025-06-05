package PageObjectTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import Page.LoginPage;
import Page.RegisterPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class RegisterUserTest {
	private static LoginPage loginPage;
	private static RegisterPage registerPage;
	private static WebDriver driver;
	private static Logger logger = LogManager.getLogger(RegisterUserTest.class);
	
	String firstName = "firstName01";
	String lastName = "lastName01";
	String username = firstName+lastName+"001";
	String password = "password";
	String expectedErrorMsg = "Please verify reCaptcha to register!";
	
	@BeforeAll
	public static void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		loginPage = new LoginPage(driver);
		registerPage = new RegisterPage(driver);
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
	}
	@Test
	public void registerNewUser() {
		loginPage.load();
		loginPage.registerNewUser();
		registerPage.fillFirstName(firstName);
		registerPage.fillLastName(lastName);
		registerPage.fillUsername(username);
		registerPage.fillPassword(password);
		registerPage.register();
		assertEquals(expectedErrorMsg,registerPage.getErrorMessage());
	}
	@AfterAll
	public static void after() {
		if(driver!=null) {
			try {
				driver.quit();
			} catch (Exception e) {
				logger.error("Error: " + e.getMessage());
			}
		}
	}
}
