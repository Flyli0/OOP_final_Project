package service;

import java.util.List;
import java.util.Optional;

import config.DbContext;
import model.Student;
import model.User;

public class AuthService {
	private static DbContext db = DbContext.getInstance();
	public static User login(String login, String password) {
		List<User> users = DbContext.getInstance().allUsers();
		return users.stream()
				.filter(user -> 
					user.getLogin().equals(login) && 
					user.getPassword().equals(password))
				.findFirst()
				.orElse(null);
	}

	public static User signUp(String login, String password, AccountType at) {
		User u = AccountFactory.createAccount(at, login, password);
		db.addUser(u);
		if(u instanceof Student) {
			db.addStudent((Student) u);
		}
		return u;
	}
}
	