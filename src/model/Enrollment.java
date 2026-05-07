package model;
import java.util.List;
import java.util.Map;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Enrollment implements Serializable{
	private Course course;
	private boolean is_open;
	private Map<Student,Boolean> students;
	private List<Teacher> instructors;
	
	
	{
		this.students = new HashMap<Student,Boolean>();
		this.instructors = new ArrayList<>();
	}
	
	public Enrollment(Course course) {
		this.course = course;
		this.is_open = true;
	}
	
	public void addStudent(Student s) {
		this.students.put(s, false);
	}
	
	public void removeStudent(Student s) {
		this.students.remove(s);
	}
	
	public void addTeacher(Teacher t) {
		this.instructors.add(t);
	}

	public Course getCourse() {
		return course;
	}
	
	public List<Teacher> getTeachers(){
		return this.instructors;
	}

	public List<Teacher> getInstructors() {
		return instructors;
	}
	
	public void closeEnrollment() {
		this.is_open = false;
	}
	
	public void setStudents(HashMap<Student,Boolean> students) {
		this.students = students;
	}
	
	public HashMap<Student,Boolean> getStudents(){
		return (HashMap<Student,Boolean>) this.students;
	}
	
	public boolean getAvailability() {
		return this.is_open;
	}
}
