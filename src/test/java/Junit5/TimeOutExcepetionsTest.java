package Junit5;

import java.util.concurrent.TimeUnit;

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
}
