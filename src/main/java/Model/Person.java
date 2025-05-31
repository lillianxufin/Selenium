package Model;

public class Person {
	private String name;
	private String email;
	private String role;
	public Person(String name, String role) {
		this.name = name;
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public String getRole() {
		return role;
	}
	public Object getMajor() {
		// TODO Auto-generated method stub
		return null;
	}
}
