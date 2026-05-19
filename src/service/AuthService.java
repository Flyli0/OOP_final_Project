package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import config.DbContext;
import model.Gender;
import model.Student;
import model.User;
import views.Core;

public class AuthService {
	private static DbContext db = DbContext.getInstance();
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static User login(String login, String password) {
		List<User> users = DbContext.getInstance().allUsers();
		return users.stream()
				.filter(user ->
					login.equals(user.getLogin()) &&
					password.equals(user.getPassword())) 
				.findFirst()
				.orElse(null);
	}


	public static User signUp(String login, String password, AccountType at) throws IOException, ParseException {
		User u = AccountFactory.createAccount(at, login, password);
		FTAccountSetting(u);
		db.addUser(u);
		if(u instanceof Student) {
			db.addStudent((Student) u);
		}
		return u;
	}
	
	//---------------------ACCOUNT SETTINGS AFTER SIGNING UP 
	private static void FTAccountSetting(User u) throws  IOException, ParseException {
		String name = null;
		String surname = null;
		Date birthday = null;
		System.out.println("Enter your name");
		name = br.readLine();
		u.setName(name);
		System.out.println("Enter your surname");
		surname = br.readLine();
		u.setSurname(surname);
		System.out.println("Enter your birth date: DD-MM-YYYY");
		String datestr = br.readLine();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date d = sdf.parse(datestr);
		birthday = d;
		System.out.println("Enter your gender: 1>Male 2>Female");
		String c = br.readLine();
		switch(c) {
		case("1"): u.setGender(Gender.MALE);break;
		case("2"): u.setGender(Gender.FEMALE);break;
		}
		u.setLanguage("en");
		db.saveUsers();
		db.saveStudents();
	}
}
	