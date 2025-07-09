package Thread;

import org.junit.jupiter.api.Test;
import Core.BaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoogleSearchTest extends BaseTest {
    @Test
    public void testGoogleSearch1() {
        // Use the thread-safe driver from the BaseTest
        getDriver().get("https://www.google.com");
        assertEquals("Google", getDriver().getTitle());
        System.out.println("Test 1 completed on thread: " + Thread.currentThread().getId());
    }

    @Test
    public void testGoogleSearch2() {
        getDriver().get("https://www.google.com");
        assertEquals("Google", getDriver().getTitle());
        System.out.println("Test 2 completed on thread: " + Thread.currentThread().getId());
    }
    @Test
    public void testGoogleSearch3() {
        getDriver().get("https://www.google.com");
        assertEquals("Google", getDriver().getTitle());
        System.out.println("Test 3 completed on thread: " + Thread.currentThread().getId());
    }
    // Add more tests...
}