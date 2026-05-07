package config;
import model.User;
import java.util.Map;
import model.Admin;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.io.File;
import model.Course; 
import model.Enrollment;
import model.Student;


public class DbContext {
	private static String path;
	private static final DbContext INSTANCE = new DbContext();
	private static List<Course> courses;
	private static List<Enrollment> enrollments;
	private static List<User> users;
	private static List<Student> students;
	
	private DbContext() {
		path = new File("src/data").getAbsolutePath();
		
		courses = (List<Course>) DbContext.loadCourses();
		courses = (List<Course>) ValidateDb.validate(courses);
		
		students = (List<Student>) DbContext.loadStudents();
		students = (List<Student>) ValidateDb.validate(students);
		
		enrollments = (List<Enrollment>) DbContext.loadEnrollments();
		enrollments = (List<Enrollment>) ValidateDb.validate(enrollments);
		
		users = new ArrayList<User>();
		users = (List<User>) DbContext.loadUsers();
		
		//System.out.println(users);
		if(users == null) {
			users = new ArrayList<User>();
			User admin = new Admin("John","Doe", 0.0, new Date(System.currentTimeMillis()	) ,"Qwerty", "1234");
			users.add(admin);
			DbContext.saveUsers();
		}
	}
	
	public static DbContext getInstance() {
		return INSTANCE;
	}
	
	public static boolean serialize(Object o, String fileName) {
		try(ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(path + "/" + fileName + ".txt"))){
			oos.writeObject(o);
			oos.close();
			return true;
		}
		catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return false;
	}
	
	public static Object deserialize(String fileName) {
		try(ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(path + "/" + fileName + ".txt"))){
			Object o = ois.readObject();
			ois.close();
			return o;
		}
		catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
		catch(ClassNotFoundException cnfe) {
			System.out.println("Class not found!");
		}
		return null;
	}
//			✎﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏    ꧁ ༺SAVE༻ ꧂   ﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏𓊝﹏𓂁﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏
	public static boolean saveUsers() {
		DbContext.serialize(users, "users");
		return true;
	}
	
	public static boolean saveEnrollments() {
		DbContext.serialize(enrollments, "enrollments");
		return true;
	}
	
	public static boolean saveStudents() {
		DbContext.serialize(students, "students");
		return true;
	}
	
	public static boolean saveCourses() {
		DbContext.serialize(courses, "courses");
		return true;
	}
//			✎﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏𓊝﹏𓂁﹏﹏   ꧁ ༺LOAD༻ ꧂   ﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏	
	public static Object loadUsers() {
		Object ret = DbContext.deserialize("users");
		return ret;
	}
	
	public static Object loadEnrollments() {
		Object ret = DbContext.deserialize("enrollments");
		return ret;
	}
	
	public static Object loadStudents() {
		Object ret = DbContext.deserialize("students");
		return ret;
	}
	
	public static Object loadCourses() {
		Object ret = DbContext.deserialize("courses");
		return ret;
	}
//		✎﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏   ꧁ ༺LIST_GETTERS༻ ꧂   ﹏﹏﹏﹏﹏﹏﹏𓊝﹏𓂁﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏
	public List<User> allUsers(){
		return this.users;
	}
	
	public List<Enrollment> allEnrollments(){
		return this.enrollments;
	}
	
	public List<Student> allStudent(){
		return this.students;
	}
	
	public List<Course> allCourses(){
		return this.courses;
	}
	
// 		✎﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏	꧁ ༺ ADDERS ༻ ꧂   ﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏﹏
	public void addUser(User u){
		DbContext.users.add(u);
		DbContext.saveUsers();
	}
	
	public void addEnrollment(Enrollment en) {
		DbContext.enrollments.add(en);
		DbContext.saveEnrollments();
	}
	
	public void addStudent(Student s) {
		DbContext.students.add(s);
		DbContext.saveStudents();
	}
	
	public void addCourse(Course c) {
		DbContext.courses.add(c);
		DbContext.saveCourses();
	}
}
