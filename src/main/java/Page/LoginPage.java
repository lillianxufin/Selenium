package Page;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	private WebDriver driver;
    private WebDriverWait wait;
    private String loginPageUrl = "https://demoqa.com/login";
    
    private By newUserBy = By.id("newUser");
	
	public LoginPage(WebDriver driver) {
		this.driver =driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public void load() {
		driver.get(loginPageUrl);
	}
	
	public void registerNewUser() {
		wait.until(ExpectedConditions.elementToBeClickable(newUserBy)).click();
	}
}
