package views;
import service.AuthService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import config.DbContext;
import service.AccountType;
import model.User;

public class Core {
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static DbContext db = DbContext.getInstance();
	public static void run() throws IOException {
		System.out.println(db.loadUsers());
		System.out.println("Welcome to University!");
		System.out.println("Please Authorize!");
		System.out.println("To sign up Enter 1  \nTo log in Enter 2");
		String answer = br.readLine();
		User currentUser = null;
		System.out.println(answer);
		if(Integer.parseInt(answer) == 1) {
			currentUser = signup();
		}
		else if(Integer.parseInt(answer) ==  2) {
			currentUser = auth();
		}
		else {
			System.out.println("Unexistent option");
			Core.run();
		}
		
				
		if(currentUser!=null) {
			mainPage();
		}
		else{
			System.err.println("Username or password are not valid!");
		}
	}
	
	public static User auth() throws IOException {
		System.out.println("Enter your username!");
		String username = br.readLine();
		System.out.println("Enter your password");
		String password = br.readLine();
		User currUser = AuthService.login(username,password);
		return currUser;
	}
	
	public static User signup() throws IOException {
		System.out.println("Enter your username!");
		String username = br.readLine();
		System.out.println("Enter your password");
		String password = br.readLine();
		System.out.println("Choose your Account type: ");
		System.out.println("0 - Admin \n 1-Manager \n 2-Technical Specialist \n 3-Teacher \n 4-Student Bachelor \n 5-Master grade student \n 6-PhD Student");
		String accTypeChoice = br.readLine();
		AccountType at = null;
		int accTypeChoiceInt = Integer.parseInt(accTypeChoice);
		switch(accTypeChoiceInt) {
		case(0) -> at = AccountType.ADMIN;
		case(1) -> at = AccountType.MANAGER;
		case(2) -> at = AccountType.TECH;
		case(3) -> at = AccountType.TEACHER;
		case(4) -> at = AccountType.STUDENT;
		case(6) -> at = AccountType.PHD;
		case(5) -> at = AccountType.MASTER;
		}
		User currUser = AuthService.signUp(username,password,at);
		return currUser;
	}
	
	public static void mainPage() {
		System.out.println("WELCOME!!!");
		System.out.println(db.loadUsers());
	}
}
