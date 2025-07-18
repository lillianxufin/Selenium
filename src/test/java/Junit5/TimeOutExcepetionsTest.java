package Junit5;

import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class TimeOutExcepetionsTest {
	
	    @Test
	    @Timeout(value = 10, unit = TimeUnit.SECONDS)
	    void whenTimeout_thenTestPasses() throws InterruptedException {
	        TimeUnit.SECONDS.sleep(9); // This will cause the test to fail due to timeout
	    }
	  
	     
	    @Test
	    @Timeout(value = 10, unit = TimeUnit.SECONDS)
	    void longRunningMethodWithTimeout() throws InterruptedException {
	        TimeUnit.SECONDS.sleep(20); // This will cause timeout
	    }
	    
	    @Test
	    void testAssertTimeout() {
	        assertTimeout(Duration.ofSeconds(10), () -> {
	        	TimeUnit.SECONDS.sleep(10);
	        });
	    }
	    
}
