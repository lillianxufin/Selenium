package Junit5;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class TestInstanceTest {
	private Logger logger = LogManager.getLogger(TestInstance.class);
	private int count=0;

	
	@Test
	public void test1() {
		count++;
		logger.info(count);
	}
	
	@Test
	public void test2() {
		count++;
		logger.info(count);
	}
}
