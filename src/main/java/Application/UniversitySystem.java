package Application;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Model.Person;
import Model.Staff;
import Model.Student;
import Service.DatabaseService;
import Service.EmailService;

public class UniversitySystem {
    private DatabaseService database;
    private EmailService emailService;
    
    public UniversitySystem(DatabaseService database, EmailService emailService) {
        this.database = database;
        this.emailService = emailService;
    }
    
    public void enrollStudent(String name, Long id, String major, String email) {
        Student student = new Student(name, "Student", id, major);
        student.setEmail(email);
        database.saveStudent(student);
    }
    
    public void hireStaff(String name, String role, String email) {
        Staff staff = new Staff(name, role);
        staff.setEmail(email);
        database.saveStaff(staff);
    }
    
    public boolean sendWelcomeEmails() {
        List<Person> allPeople = database.getAllPeople();
        int emailsSent = 0;
        
        for (Person person : allPeople) {
            if (person.getEmail() != null) {
                String subject = "Welcome to University";
                String body = "Welcome " + person.getName() + " (" + person.getRole() + ")";
                if (emailService.sendEmail(person.getEmail(), subject, body)) {
                    emailsSent++;
                }
            }
        }
        
        return emailsSent > 0;
    }
    
    public List<Student> getStudentsByMajor(String major) {
        return database.findStudentsByMajor(major);
    }
    
    public Map<String, Integer> getMajorDistribution() {
        return database.getAllPeople().stream()
            .filter(person -> person.getRole().equals("Student"))
            .map(person -> ((Student) person).getMajor())
            .collect(Collectors.groupingBy(
                major -> major,
                Collectors.collectingAndThen(Collectors.counting(), Math::toIntExact)
            ));
    }
}