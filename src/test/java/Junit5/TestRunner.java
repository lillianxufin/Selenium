package Junit5;

import org.junit.platform.suite.api.Suite;

import UnitTest.NumberComputingTest;
import UnitTest.NumberComputingTest2;

import org.junit.platform.suite.api.SelectClasses;

@Suite
@SelectClasses({
    NumberComputingTest.class,
    NumberComputingTest2.class
})
public class TestRunner {
    // No need for main method in JUnit 5 test suites
    // The suite will be executed by the JUnit Platform
}