package Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Model.Person;
import Model.Staff;
import Model.Student;

public class DatabaseService {
    private Map<Long, Student> students = new HashMap<>();
    private Map<String, Staff> staff = new HashMap<>();
    
    public void saveStudent(Student student) {
        students.put(student.getId(), student);
    }
    
    public void saveStaff(Staff staffMember) {
        staff.put(staffMember.getName(), staffMember);
    }
    
    public Student findStudentById(Long id) {
        return students.get(id);
    }
    
    public Staff findStaffByName(String name) {
        return staff.get(name);
    }
    
    public List<Student> findStudentsByMajor(String major) {
        return students.values().stream()
            .filter(s -> major.equals(s.getMajor()))
            .collect(Collectors.toList());
    }
    
    public List<Person> getAllPeople() {
        List<Person> allPeople = new ArrayList<>();
        allPeople.addAll(students.values());
        allPeople.addAll(staff.values());
        return allPeople;
    }
}