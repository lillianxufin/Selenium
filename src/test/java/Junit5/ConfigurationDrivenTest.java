package Junit5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import Model.Person;
import Model.Staff;
import Model.Student;
/*
 * Test valid input
 */
public class ConfigurationDrivenTest {
    
    static class TestConfiguration {
        private final Person person;
        private final String expectedRole;
        private final String expectedMajor;
        private final boolean shouldHaveMajor;
        
        private TestConfiguration(Person person, String expectedRole, String expectedMajor, boolean shouldHaveMajor) {
            this.person = person;
            this.expectedRole = expectedRole;
            this.expectedMajor = expectedMajor;
            this.shouldHaveMajor = shouldHaveMajor;
        }
        
        static TestConfiguration forStudent(String name, long id, String major) {
            return new TestConfiguration(
                new Student(name, "Student", id, major),
                "Student", 
                major, 
                true
            );
        }
        
        static TestConfiguration forStaff(String name) {
            return new TestConfiguration(
                new Staff(name, "Staff"),
                "Staff",
                null,
                false
            );
        }
        
        // Getters
        Person getPerson() { return person; }
        String getExpectedRole() { return expectedRole; }
        String getExpectedMajor() { return expectedMajor; }
        boolean shouldHaveMajor() { return shouldHaveMajor; }
        
        @Override
        public String toString() {
            return String.format("%s (%s)", person.getName(), expectedRole);
        }
    }
    
    @ParameterizedTest(name = "{0}")//calls TestConfigration toString()
    @MethodSource("testConfigurations")
    void testPersonWithConfiguration(TestConfiguration config) {
        Person person = config.getPerson();
        
        Assertions.assertAll("Person validation",
            () -> assertEquals(config.getExpectedRole(), person.getRole()),
            () -> {
                if (config.shouldHaveMajor()) {
                    assertNotNull(person.getMajor(), "Student should have major");
                    assertEquals(config.getExpectedMajor(), person.getMajor());
                } else {
                    assertNull(person.getMajor(), "Staff should not have major");
                }
            }
        );
    }
    
    static Stream<TestConfiguration> testConfigurations() {
        return Stream.of(
            TestConfiguration.forStudent("John", 123L, "Math"),
            TestConfiguration.forStudent("Lucy", 124L, "Art"),
            TestConfiguration.forStaff("Mary")
        );
    }
}