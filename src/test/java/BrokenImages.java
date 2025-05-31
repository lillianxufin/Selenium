import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;

public class BrokenImages {
    
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        
        try {
            driver.manage().window().maximize();
            driver.get("https://demoqa.com/broken");
            
            List<WebElement> images = driver.findElements(By.tagName("img"));
            System.out.println("Total images: " + images.size());
            
            for (int i = 0; i < images.size(); i++) {
                WebElement image = images.get(i);
                String imageURL = image.getAttribute("src");
                
                System.out.println("Image " + (i + 1) + ": " + imageURL);
                
                boolean imageDisplayed = (Boolean) ((JavascriptExecutor) driver)
                    .executeScript("return (typeof arguments[0].naturalWidth !== 'undefined' && arguments[0].naturalWidth > 0);", image);
                
                if (imageDisplayed) {
                    System.out.println("STATUS: OK");
                } else {
                    System.out.println("STATUS: BROKEN");
                }
                
                System.out.println("---");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}