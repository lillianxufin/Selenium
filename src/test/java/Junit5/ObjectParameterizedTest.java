package Junit5;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import Model.Person;
import Model.Student;
import Model.Staff;

public class ObjectParameterizedTest {

    @ParameterizedTest(name = "Person: {0}, Role: {1}, Major: {2}, ID: {3}")
    @CsvSource({
        "John, Student, Math, 123",
        "Lucy, Student, Art, 124", 
        "Mary, Staff, '', 0",
        "'', Student, CS, 100",           // Empty name
        "Jane, Student, '', 101",         // Empty major
    })
    @DisplayName("Test person object data storage with CSV")
    void testPersonWithCsv(String name, String role, String major, long id) {
        Person person = createPerson(name, role, major, id);

        // Test basic property assignments
        assertEquals(name, person.getName());
        assertEquals(role, person.getRole());

        if (person instanceof Student) {
            Student student = (Student) person;
            assertEquals(id, student.getId());
            assertEquals(major, student.getMajor());
        } else if (person instanceof Staff) {
            assertNull(person.getMajor());
        }
    }
    
    @ParameterizedTest(name = "Invalid Student: {0}, {1}, {2}, {3}")
    @CsvSource({
        "Tom, Student, CS, -5",
        "Anna, Student, Math, 0",
        "Rick, Teacher, '', 1"            // Unknown role
    })
    @DisplayName("Test student creation with invalid ID throws exception")
    void testInvalidStudentId(String name, String role, String major, long id) {
        assertThrows(IllegalArgumentException.class, () -> {
            createPerson(name, role, major, id);
        });
    }
    
    private Person createPerson(String name, String role, String major, long id) {
        return switch (role) {
            case "Student" -> new Student(name, role, id, major);
            case "Staff"   -> new Staff(name, role);
            // case "Admin" -> return new Admin(name, role); // in future
            default -> throw new IllegalArgumentException("Unsupported role: " + role);
        };
    }

}
