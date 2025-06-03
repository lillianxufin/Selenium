package Junit5;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ExceptionsTest {
	private static WebDriver driver;
	@BeforeAll
	public static void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}
	@Test
	void testDetailedException() {
	    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	        throw new IllegalArgumentException("This is illegal", new NullPointerException());
	    });
	    assertAll(
	        () -> assertEquals("This is illegal", exception.getMessage()),
	        () -> assertTrue(exception.getCause() instanceof NullPointerException),
	        () -> assertNotNull(exception.getCause())
	    );
	}
	
	@Test
	void testNoSuchElementException() {
		driver.get("https://demoqa.com/buttons");
		
		// This will pass because we expect NoSuchElementException
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            WebElement ele = driver.findElement(By.id("noSuchButton"));
        });
	}
	
	@Test
	void UnsupportedCommandException() {
driver.get("https://demoqa.com/buttons");
		
		// This will pass because we expect NoSuchElementException
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            WebElement ele = driver.findElement(By.id("K6AgH"));
            Select select = new Select(ele);
        });
	}
	
	@AfterAll
	public static void after() {
		if(driver!=null) {
			try {
				driver.quit();
			} catch(Exception e) {
				
			}
		}
	}
}
