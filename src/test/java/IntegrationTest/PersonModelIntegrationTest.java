package IntegrationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Application.UniversitySystem;
import Model.Person;
import Model.Staff;
import Model.Student;
import Service.DatabaseService;
import Service.EmailService;

public class PersonModelIntegrationTest {
    
    private UniversitySystem universitySystem;
    private DatabaseService database;
    private EmailService emailService;
    
    @BeforeEach
    void setUp() {
        database = new DatabaseService();
        emailService = new EmailService();
        universitySystem = new UniversitySystem(database, emailService);
    }
    
    @Test
    void testCompleteStudentEnrollmentWorkflow() {
        // Integration test: Student enrollment through the system
        universitySystem.enrollStudent("John Doe", 12345L, "Computer Science", "john.doe@student.edu");
        universitySystem.enrollStudent("Jane Smith", 12346L, "Mathematics", "jane.smith@student.edu");
        
        // Verify student was saved correctly
        Student retrievedStudent = database.findStudentById(12345L);
        assertNotNull(retrievedStudent);
        assertEquals("John Doe", retrievedStudent.getName());
        assertEquals("Computer Science", retrievedStudent.getMajor());
        assertEquals("john.doe@student.edu", retrievedStudent.getEmail());
        assertEquals("Student", retrievedStudent.getRole());
        
        // Test querying by major
        List<Student> csStudents = universitySystem.getStudentsByMajor("Computer Science");
        assertEquals(1, csStudents.size());
        assertEquals("John Doe", csStudents.get(0).getName());
    }
    
    @Test
    void testCompleteStaffHiringWorkflow() {
        // Integration test: Staff hiring through the system
        universitySystem.hireStaff("Dr. Wilson", "Professor", "dr.wilson@university.edu");
        universitySystem.hireStaff("Ms. Johnson", "Administrator", "ms.johnson@university.edu");
        
        // Verify staff was saved correctly
        Staff retrievedStaff = database.findStaffByName("Dr. Wilson");
        assertNotNull(retrievedStaff);
        assertEquals("Dr. Wilson", retrievedStaff.getName());
        assertEquals("Professor", retrievedStaff.getRole());
        assertEquals("dr.wilson@university.edu", retrievedStaff.getEmail());
        
        // Verify getMajor returns null for staff (inherited behavior)
        assertNull(retrievedStaff.getMajor());
    }
    
    @Test
    void testCrossSystemEmailIntegration() {
        // Setup mixed population
        universitySystem.enrollStudent("Alice Brown", 11111L, "Physics", "alice@student.edu");
        universitySystem.enrollStudent("Bob Green", 11112L, "Chemistry", "bob@student.edu");
        universitySystem.hireStaff("Prof. White", "Professor", "prof.white@university.edu");
        universitySystem.hireStaff("Admin Black", "Administrator", null); // No email
        
        // Test system-wide email sending
        boolean emailsSent = universitySystem.sendWelcomeEmails();
        assertTrue(emailsSent);
        
        // Verify emails were sent to people with email addresses
        Set<String> sentEmails = emailService.getSentEmails();
        assertEquals(3, sentEmails.size()); // 3 people with emails
        assertTrue(sentEmails.contains("alice@student.edu"));
        assertTrue(sentEmails.contains("bob@student.edu"));
        assertTrue(sentEmails.contains("prof.white@university.edu"));
        assertFalse(sentEmails.contains(null));
    }
    
    @Test
    void testMajorDistributionAcrossSystem() {
        // Setup students with various majors
        universitySystem.enrollStudent("Student1", 1L, "Computer Science", "s1@edu");
        universitySystem.enrollStudent("Student2", 2L, "Computer Science", "s2@edu");
        universitySystem.enrollStudent("Student3", 3L, "Mathematics", "s3@edu");
        universitySystem.enrollStudent("Student4", 4L, "Physics", "s4@edu");
        universitySystem.enrollStudent("Student5", 5L, "Mathematics", "s5@edu");
        
        // Add staff (should not affect major distribution)
        universitySystem.hireStaff("Staff1", "Professor", "staff1@edu");
        
        // Test major distribution calculation
        Map<String, Integer> distribution = universitySystem.getMajorDistribution();
   
        assertEquals(3, distribution.size());
        assertEquals(2, distribution.get("Computer Science"));
        assertEquals(2, distribution.get("Mathematics"));
        assertEquals(1, distribution.get("Physics"));
        assertFalse(distribution.containsKey("Professor")); // Staff roles shouldn't appear
    }
    
    @Test
    void testSystemWithInvalidStudentData() {
        // Test that system properly handles validation errors
        assertThrows(IllegalArgumentException.class, () -> {
            universitySystem.enrollStudent("Invalid Student", null, "Math", "email@test.com");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            universitySystem.enrollStudent("Invalid Student", 0L, "Math", "email@test.com");
        });
        
        // Verify no invalid data was saved
        assertNull(database.findStudentById(0L));
        assertEquals(0, database.getAllPeople().size());
    }
    
    @Test
    void testLargeScaleSystemIntegration() {
        // Test system performance with larger dataset
        
        // Enroll 100 students across different majors
        String[] majors = {"Computer Science", "Mathematics", "Physics", "Chemistry", "Biology"};
        for (int i = 1; i <= 100; i++) {
            String major = majors[i % majors.length];
            universitySystem.enrollStudent(
                "Student" + i, 
                (long) i, 
                major, 
                "student" + i + "@university.edu"
            );
        }
        
        // Hire 20 staff members
        String[] roles = {"Professor", "Administrator", "Librarian", "Counselor"};
        for (int i = 1; i <= 20; i++) {
            String role = roles[i % roles.length];
            universitySystem.hireStaff(
                "Staff" + i, 
                role, 
                "staff" + i + "@university.edu"
            );
        }
        
        // Test system operations with large dataset
        List<Person> allPeople = database.getAllPeople();
        assertEquals(120, allPeople.size());
        
        // Test major distribution
        Map<String, Integer> distribution = universitySystem.getMajorDistribution();
        assertEquals(5, distribution.size());
        assertEquals(20, distribution.get("Computer Science")); // 100 students / 5 majors = 20 each
        
        // Test email system with large dataset
        boolean emailsSent = universitySystem.sendWelcomeEmails();
        assertTrue(emailsSent);
        assertEquals(120, emailService.getSentEmails().size());
        
        // Test querying specific major
        List<Student> csStudents = universitySystem.getStudentsByMajor("Computer Science");
        assertEquals(20, csStudents.size());
    }
    
    @Test
    void testPolymorphicBehaviorInSystemContext() {
        // Test that polymorphism works correctly in system operations
        universitySystem.enrollStudent("Poly Student", 999L, "Engineering", "poly@student.edu");
        universitySystem.hireStaff("Poly Staff", "Dean", "poly@staff.edu");
        
        List<Person> allPeople = database.getAllPeople();
        
        // Test that we can process all people polymorphically
        for (Person person : allPeople) {
            assertNotNull(person.getName());
            assertNotNull(person.getRole());
            assertNotNull(person.getEmail());
            
            // Test overridden getMajor method
            if (person instanceof Student) {
                assertNotNull(person.getMajor()); // Student should return actual major
            } else {
                assertNull(person.getMajor()); // Staff should return null
            }
        }
    }
}