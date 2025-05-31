package Model;

public class Student extends Person{
	String name;
	Long id;
	String major;
	public Student(String name, String role, Long id, String major) {
		super(name, role);
		if (id == null || id <= 0) {
            throw new IllegalArgumentException("Student ID must be greater than 0");
        }
		this.id = id;
		this.major = major;
	}
	public String getMajor() {
		return major;
	}
	public long getId() {
		return id;
	}
}
