package Listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.events.WebDriverListener;

import utils.ScreenshotUntils;

public class EventHandler implements WebDriverListener {
    private static final Logger logger = LogManager.getLogger(EventHandler.class);
    private static ScreenshotUntils screenshotUntils = new ScreenshotUntils();
    // Before events
    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        logger.debug("About to find element with locator: " + locator);
    }
    
    @Override
    public void beforeClick(WebElement element) {
        logger.info("About to click on element: " + getElementDescription(element));
    }
    
    @Override
    public void beforeGet(WebDriver driver, String url) {
        logger.info("About to navigate to: " + url);
    }
    
    @Override
    public void beforeExecuteScript(WebDriver driver, String script, Object[] args) {
        logger.debug("About to execute script: " + script);
    }
    
    // After events
    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        logger.debug("Found element with locator: " + locator);
    }
    
    @Override
    public void afterClick(WebElement element) {
        logger.info("Clicked on element: " + getElementDescription(element));
    }
    
    @Override
    public void afterGet(WebDriver driver, String url) {
        logger.info("Navigated to: " + driver.getCurrentUrl());
    }
    
    @Override
    public void afterExecuteScript(WebDriver driver, String script, Object[] args, Object result) {
        logger.debug("Executed script: " + script);
    }
    
    // Send Keys Events
    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        logger.info("Sending keys to element: " + getElementDescription(element) + ", keys: " + Arrays.toString(keysToSend));
    }
    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        logger.info("Sent keys to element: " + getElementDescription(element));
    }
   
 // Actions Class Events
    @Override
    public void beforePerform(WebDriver driver, Collection<Sequence> actions) {
        // Simple logging without reflection or modifying the actions
        int count = actions != null ? actions.size() : 0;
        logger.info("About to perform " + count + " action sequence(s)");
    }

    @Override
    public void afterPerform(WebDriver driver, Collection<Sequence> actions) {
        logger.info("Actions performed successfully");
    }

    // Exception handling
    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        logger.error("Exception occurred during " + method.getName() + ": " + e.getTargetException().getMessage());
        
        if (target instanceof WebDriver) {
            WebDriver driver = (WebDriver) target;
           screenshotUntils.capturePageScreenshot(driver, "error_" + method.getName());
        }
    }
    
    // Helper methods
    private String getElementDescription(WebElement element) {
        StringBuilder description = new StringBuilder();
        try {
            if (element.getTagName() != null) {
                description.append(element.getTagName());
            }
            
            if (element.getAttribute("id") != null && !element.getAttribute("id").isEmpty()) {
                description.append(" id=").append(element.getAttribute("id"));
            } else if (element.getAttribute("name") != null && !element.getAttribute("name").isEmpty()) {
                description.append(" name=").append(element.getAttribute("name"));
            } else if (element.getText() != null && !element.getText().isEmpty() && element.getText().length() < 30) {
                description.append(" text='").append(element.getText()).append("'");
            }
        } catch (Exception e) {
            return element.toString();
        }
        
        return description.length() > 0 ? description.toString() : element.toString();
    }

}