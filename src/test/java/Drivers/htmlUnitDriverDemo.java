package Drivers;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
public class htmlUnitDriverDemo {
	public static void main(String[] args) {
        // Declaring and initialising the HtmlUnitWebDriver
        HtmlUnitDriver unitDriver = new HtmlUnitDriver(BrowserVersion.FIREFOX);
        
        // open demo site webpage
        unitDriver.get("https://demoqa.com/");
		
	//Print the title of the page
        System.out.println("Title of the page is -> " + unitDriver.getTitle());
        
    }
}