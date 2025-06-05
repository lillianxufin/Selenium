package Junit5;

import org.junit.platform.suite.api.Suite;

import UnitTest.NumberComputingTest;
import UnitTest.NumberComputing2Test;

import org.junit.platform.suite.api.SelectClasses;

@Suite
@SelectClasses({
    NumberComputingTest.class,
    NumberComputing2Test.class
})
public class TestSuite {
    // No need for main method in JUnit 5 test suites
    // The suite will be executed by the JUnit Platform
}