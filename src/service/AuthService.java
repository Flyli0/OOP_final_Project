package service;

import java.util.List;
import java.util.Optional;

import config.DbContext;
import model.User;

public class AuthService {
	public User login(String username, String password) {
		List<User> users = DbContext.getInstance().allUsers();
		Optional<User> ou = users.stream().filter(user -> user.equals(new User(username,password,0))).findFirst();
		User curU = ou.get();
		if(curU!=null) {
			return curU;
		}
		return null;
	}
}
