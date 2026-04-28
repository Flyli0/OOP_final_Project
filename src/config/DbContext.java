package config;
import model.User;

import java.util.ArrayList;
import java.util.List;

import model.Course; 

public class DbContext {
	private static List<Course> courses;
	private static List<User> users;
	
	private DbContext() {
		courses = new ArrayList<Course>();
		users = new ArrayList<User>();
		
	}
}
