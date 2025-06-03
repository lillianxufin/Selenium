package UnitTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import Math.NumberComputing;

@TestInstance(Lifecycle.PER_CLASS)
public class NumberComputingTest2 {
	private NumberComputing numberComputing;
	Logger logger = LogManager.getLogger(NumberComputingTest2.class);
	@BeforeAll
	public void setup() {
		numberComputing = new NumberComputing();
	}
	@Test
	@DisplayName("Multiple Test")
	public void multiple(TestInfo testInfo)
	{   
		logger.info("Starting test " + testInfo.getDisplayName());
	    int firstNumber = 10;
	    int secondNumber = 20;
		Assertions.assertEquals(firstNumber * secondNumber, numberComputing.multiple(firstNumber, secondNumber));
		logger.info("Multiple test passed.");
	}
}
