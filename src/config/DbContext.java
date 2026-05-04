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
import java.sql.Date;
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
		path = new File("src/data").getAbsolutePath();
		users = new ArrayList<User>();
		users = (List<User>) DbContext.loadUsers();
		System.out.println(users);
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
	
	public static boolean saveUsers() {
		DbContext.serialize(users, "users");
		return true;
	}
	
	public static Object loadUsers() {
		Object ret = DbContext.deserialize("users");
		return ret;
	}
	
	public List<User> allUsers(){
		return this.users;
	}
	
	public void addUser(User u){
		DbContext.users.add(u);
		DbContext.saveUsers();
	}
	
	public List<Course> allCourses(){
		return this.courses;
	}
}
