package Drivers;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class htmlUnitDriverDemoTest {

    @Test
    public void testPageTitle() {
        HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX);
        driver.get("https://demoqa.com/");
        String title = driver.getTitle();
        System.out.println("Title of the page is -> " + title);
        //assertNotNull(title); // Optional assertion
        driver.quit();
    }
}
