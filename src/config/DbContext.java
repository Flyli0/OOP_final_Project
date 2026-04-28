package config;
import model.User;
import model.Admin;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.File;
import model.Course; 


public class DbContext {
	private static String path;
	private static final DbContext INSTANCE = new DbContext();
	private static List<Course> courses;
	private static List<User> users;
	
	private DbContext() {
		courses = new ArrayList<Course>();
		users = new ArrayList<User>();
		path = new File("src/data").getAbsolutePath();
		User admin = new Admin("John","Doe", 0.0, (java.sql.Date) new Date() ,"Qwerty", "1234");
		users.add(admin);
		this.save();
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
	
	public static boolean save() {
		DbContext.serialize(users, "users");
		DbContext.serialize(courses, "courses");
		return true;
	}
	
	public static boolean load() {
		DbContext.deserialize("users");
		DbContext.deserialize("courses");
		return true;
	}
	
	public List<User> allUsers(){
		return this.users;
	}
	
	public List<Course> allCourses(){
		return this.courses;
	}
}
