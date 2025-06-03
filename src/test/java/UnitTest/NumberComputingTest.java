package UnitTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import Math.NumberComputing;

@TestInstance(Lifecycle.PER_CLASS)
public class NumberComputingTest {
	private NumberComputing numberComputing;
	Logger logger = LogManager.getLogger(NumberComputingTest.class);
	@BeforeAll
	public void setup() {
		numberComputing = new NumberComputing();
	}
	@Test
	public void add()
	{
		logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
	    int firstNumber = 10;
	    int secondNumber = 20;
		Assertions.assertEquals(firstNumber + secondNumber, numberComputing.add(firstNumber, secondNumber));
		logger.info("Add test passed.");
	}
	
	@Test
	public void substract(TestInfo testInfo) {
		logger.info("Starting test" + testInfo.getDisplayName());
		int firstNumber = 10;
		int secondNumber = 5;
		Assertions.assertEquals(firstNumber-secondNumber, numberComputing.substract(firstNumber, secondNumber));
		logger.info("Substract test passed.");
	}

}
