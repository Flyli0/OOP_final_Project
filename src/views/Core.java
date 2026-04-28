package views;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import model.User;

public class Core {
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static void run() {
		System.out.println("Welcome to University!");
		System.out.println("Please Authorize!");
		User currentUser = auth();
				
		if(currentUser!=null) {
			mainPage();
		}
		else{
			System.err.println("Username or password are not valid!");
		}
	}
	
	public static User auth() {
		System.out.println("Enter your username!");
		String username = br.readLine();
		System.out.println("Enter your password");
		String password = br.readLine();
		User currUser = AuthService.login(username,password);
		return currUser;
	}
	
	public static void mainPage() {
		System.out.println("WELCOME!!!");
	}
}
