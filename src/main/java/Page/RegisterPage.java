package Page;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {
	private WebDriver driver;
    private WebDriverWait wait;
    
	private By firstNameBy = By.id("firstname");
	private By lastNameBy = By.id("lastname");
	private By usernameBy = By.id("userName");
	private By passwordBy = By.id("password");
	private By reCaptchaBy = By.id("recaptcha-anchor");
	private By rigisterBtnBy = By.id("register");
	private By errorMsgBy = By.id("name");
	
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public void fillFirstName(String firstName) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameBy))
        .sendKeys(firstName);
	}
	
	public void fillLastName(String lastName) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameBy))
        .sendKeys(lastName);
	}
	
	public void fillUsername(String username) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(usernameBy))
        .sendKeys(username);
	}
	
	public void fillPassword(String password) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(passwordBy))
        .sendKeys(password);
	}
	
	public void register() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement registerBtn = driver.findElement(rigisterBtnBy);
		js.executeScript("arguments[0].scrollIntoView(true)", registerBtn);
		registerBtn.click();
	}
	
	public String getErrorMessage() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsgBy)).getText();
	}
}
