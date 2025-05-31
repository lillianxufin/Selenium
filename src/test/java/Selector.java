import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Selector {

    private static final Logger logger = LogManager.getLogger();
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

   @Test
    public void testDropdownSelections() {
        try {
            driver.get("https://demoqa.com/select-menu");
            Select select = new Select(driver.findElement(By.id("oldSelectMenu")));

            // Select by index
            logger.info("Select the Option by Index 4");
            select.selectByIndex(4);
            logger.info("Selected value: " + select.getFirstSelectedOption().getText());

            // Select by visible text
            logger.info("Select the Option by Text 'Magenta'");
            select.selectByVisibleText("Magenta");
            logger.info("Selected value: " + select.getFirstSelectedOption().getText());

            // Select by value
            logger.info("Select the Option by value '6'");
            select.selectByValue("6");
            logger.info("Selected value: " + select.getFirstSelectedOption().getText());

        } catch (TimeoutException e) {
            logger.error("TimeoutException occurred during testDropdownSelections", e);
        }
    }
    
    @Test
    public void testMultiSelect() {
    	// Navigate to the URL
        driver.get("https://demoqa.com/select-menu");

        //Maximizing window
        driver.manage().window().maximize();

        //Selecting the multi-select element by locating its id
        Select select = new Select(driver.findElement(By.id("cars")));


        //Using isMultiple() method to verify if the element is multi-select, if yes go onto next steps else eit
        if(select.isMultiple()){

            //Selecting option as 'Opel'-- ByIndex
            logger.info("Select option Opel by Index");
            select.selectByIndex(2);

            //Selecting the option as 'Saab'-- ByValue
            logger.info("Select option saab by Value");
            select.selectByValue("saab");

            // Selecting the option by text
            logger.info("Select option Audi by Text");
            select.selectByVisibleText("Audi");

            //Get the list of selected options
            logger.info("The selected values in the dropdown options are -");

            List<WebElement> selectedOptions = select.getAllSelectedOptions();

            for(WebElement selectedOption: selectedOptions)
                System.out.println(selectedOption.getText());


            // Deselect the value "Audi" by Index
            logger.info("DeSelect option Audi by Index");
            select.deselectByIndex(3);

            //Deselect the value "Opel" by visible text
            logger.info("DeSelect option Opel by Text");
            select.deselectByVisibleText("Opel");

            //Validate that both the values are deselected
            logger.info("The selected values after deselect in the dropdown options are -");
            List<WebElement> selectedOptionsAfterDeselect = select.getAllSelectedOptions();

            for(WebElement selectedOptionAfterDeselect: selectedOptionsAfterDeselect)
                logger.info(selectedOptionAfterDeselect.getText());

        }

    }
    
    @Test
    public void testMultiSelectDropDown() throws InterruptedException {
    	// Navigate to the URL
        driver.get("https://demoqa.com/select-menu");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,250)");
    	WebElement dropDownArrow = driver.findElements(By.cssSelector(".css-tlfecz-indicatorContainer")).get(2);
    	wait.until(ExpectedConditions.visibilityOf(dropDownArrow)).click();
    	WebElement dropDown = driver.findElement(By.cssSelector(".css-26l3qy-menu"));
    	wait.until(ExpectedConditions.visibilityOf(dropDown));
    	List<String> expected = new ArrayList<>();
    	WebElement option1 = driver.findElement(By.id("react-select-4-option-0"));
    	expected.add(option1.getText());
    	option1.click();
    	WebElement option2 = driver.findElement(By.id("react-select-4-option-1"));
    	expected.add(option2.getText());
    	option2.click();
    	 List<WebElement> selectedOptions = driver.findElements(
    	            By.cssSelector("div[class*='multiValue']"));
    	 for(int i = 0; i < selectedOptions.size(); i++) {
    		 Assertions.assertEquals(expected.get(i), selectedOptions.get(i).getText());
    	 }
    }
}
