package Junit5;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import Math.NumberComputing;

public class IntParameterizedTest {
    private static NumberComputing Numbers;
    private Logger logger = LogManager.getLogger(IntParameterizedTest.class);
    @BeforeAll
    public static void init() {
        Numbers = new NumberComputing();
    }
    
    @BeforeEach
    public void beforeEach() {
        logger.info("test started.");
    }
    
    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, -3, 15, Integer.MAX_VALUE}) // six numbers
    public void isOdd_ShouldReturnTrueForOddNumbers(int number) {
    	logger.info("Test against: " + number);
        Assertions.assertTrue(Numbers.isOdd(number));
    }
 // Using CSV source for multiple parameters
    @ParameterizedTest
    @CsvSource({
        "1, true",
        "2, false", 
        "3, true",
        "4, false"
    })
    public void testOddEven(int number, boolean expectedOdd) {
    	logger.info("Test against: " + number);
        Assertions.assertEquals(expectedOdd, Numbers.isOdd(number));
    }
    @AfterEach
    public void after() {
        logger.info("test done.");
    }
}

   
    
    
    
    