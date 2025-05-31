import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class BrokenLinks {
    
    public static void main(String[] args) {
        WebDriver driver = null;
        try {
            // Setup WebDriver
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            
            // Navigate to the test page
            driver.get("https://demoqa.com/broken");
            
            // Find all links on the page
            List<WebElement> links = driver.findElements(By.tagName("a"));
            System.out.println("Total number of links found: " + links.size());
            
            int brokenLinksCount = 0;
            int validLinksCount = 0;
            
            // Check each link
            for (int i = 0; i < links.size(); i++) {
                WebElement linkElement = links.get(i);
                String linkUrl = linkElement.getAttribute("href");
                
                // Skip empty or null URLs
                if (linkUrl == null || linkUrl.trim().isEmpty()) {
                    System.out.println("Link " + (i + 1) + ": Empty or null URL - SKIPPED");
                    continue;
                }
                
                // Skip javascript links and mailto links
                if (linkUrl.startsWith("javascript:") || linkUrl.startsWith("mailto:")) {
                    System.out.println("Link " + (i + 1) + ": " + linkUrl + " - SKIPPED (javascript/mailto)");
                    continue;
                }
                
                System.out.print("Link " + (i + 1) + ": Checking " + linkUrl + " ... ");
                
                boolean isBroken = verifyLink(linkUrl);
                if (isBroken) {
                    brokenLinksCount++;
                } else {
                    validLinksCount++;
                }
            }
            
            // Print summary
            System.out.println("=" + "=".repeat(60));
            System.out.println("SUMMARY:");
            System.out.println("Total links checked: " + (brokenLinksCount + validLinksCount));
            System.out.println("Valid links: " + validLinksCount);
            System.out.println("Broken links: " + brokenLinksCount);
            
        } catch (Exception e) {
            System.err.println("An error occurred during execution: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Ensure driver is closed
            if (driver != null) {
                driver.quit();
            }
        }
    }
    
    /**
     * Verifies if a link is broken by checking its HTTP response code
     * @param linkUrl The URL to check
     * @return true if the link is broken, false if it's valid
     */
    public static boolean verifyLink(String linkUrl) {
        try {
            URI uri = new URI(linkUrl);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Set connection properties
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET"); 
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
            
            connection.connect();
            int responseCode = connection.getResponseCode();
            String responseMessage = connection.getResponseMessage();
            
            if (responseCode >= 400) {
                System.out.println("BROKEN (" + responseCode + " - " + responseMessage + ")");
                return true;
            } else {
                System.out.println("OK (" + responseCode + " - " + responseMessage + ")");
                return false;
            }
            
        } catch (Exception e) {
            System.out.println("ERROR (" + e.getMessage() + ")");
            return true; // Consider it broken if we can't connect
        }
    }
}