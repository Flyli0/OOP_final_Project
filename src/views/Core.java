package views;
import service.AuthService;
import service.EnrollmentService;
import service.MessageSendingService; // ДОБАВЛЕНО АЗИЗОЙ
import service.NewsService;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import config.DbContext;
import service.AccountType;
import model.Course;
import model.Enrollment;
import model.Manager;
import model.Student;
import model.User;

public class Core {
	private static User currentUser;
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static DbContext db = DbContext.getInstance();

	public static void run() throws IOException {
		System.out.println("Welcome to University!");
		System.out.println("Please Authorize!");
		System.out.println("To sign up Enter 1  \nTo log in Enter 2");
		String answer = br.readLine();
			currentUser = null;
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

	//--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_-LOGIN AND SIGNUP-_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_
	public static User auth() throws IOException {
		System.out.println("Enter your username!");
		String username = br.readLine();
		System.out.println("Enter your password");
		String password = br.readLine();
		currentUser = AuthService.login(username,password);
		return currentUser;
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

	// АЗИЗА: ОБНОВЛЕННАЯ ГЛАВНАЯ СТРАНИЦА С МЕНЮ
	public static void mainPage() throws IOException {
		System.out.println("WELCOME!!!");


	/*	while(true) {
			System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
			System.out.println("1. Управление Enrollment (Код Кирилла)");
			System.out.println("2. Почта и Сообщения (Твой код)");
			System.out.println("3. Выход");
			System.out.print("Выбери действие: ");

			String choice = br.readLine();

			if (choice.equals("1")) {
				enrollment();
			} else if (choice.equals("2")) {
				messagesMenu(); // Вызов твоего меню
			} else if (choice.equals("3") || choice.toLowerCase().equals("exit")) {
				System.out.println("Выход из системы...");
				break;
			} else {
				System.out.println("Неверная команда, попробуй еще раз."); */

		System.out.println(db.loadUsers());
		System.out.println(db.loadEnrollments());
		if(currentUser instanceof Manager) {
			System.out.println("Hello Manager! \n choose your move: \n1>Manage enrollments \n2>Manage news");
			String input = br.readLine();
			switch(input) {
			case "1": enrollment();
			//case "2": newsManage();
			default: System.out.println("Wrong format!");

			}
		}
	}

	// АЗИЗА:БЛОК ДЛЯ РАБОТЫ С СООБЩЕНИЯМИ
	public static void messagesMenu() throws IOException {
		MessageSendingService msgService = new MessageSendingService();
		System.out.println("\n--- МЕНЮ СООБЩЕНИЙ ---");
		System.out.println("1. Написать сообщение");
		System.out.println("2. Прочитать мои входящие");
		System.out.print("Выбор: ");
		String ans = br.readLine();

		if (ans.equals("1")) {
			msgService.sendMessage();
		} else if (ans.equals("2")) {
			msgService.viewMyMessages();
		}
	}

	//--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_-ENROLLMENT FOR MANAGERS-_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_
	public static void enrollment() throws IOException {
		Enrollment currentEnrollment = new Enrollment(new Course());
		while(true) {
			System.out.println("Choose course (type Name): \n!!!to Quit enter \"exit\"");
			for(Course c: db.allCourses()) {
				System.out.println(c.getCourseName());
			}
			String input = br.readLine();
			if(input.toLowerCase().equals("exit")) {
				break;
			}
			Course currentCourse = db.allCourses().stream().filter(c->c.getCourseName().equals(input)).findFirst().orElse(null);
			if(EnrollmentService.getEnrollment(currentCourse)==null) {
				currentEnrollment = EnrollmentService.createEnrollment(currentCourse);
				System.out.println("Enrollment Created!");
			}
			else {
				currentEnrollment = EnrollmentService.getEnrollment(currentCourse);
				System.out.println("Enrollment is Found!");
			}
			System.out.println("What do you want to do? \n1>Add Student to the enrollment \n2>Approve registration for students of the enrollment");
			String answer = br.readLine();
			if(answer.toLowerCase().equals("exit")) {
				break;
			}
			if(answer.equals("1")) {
				while(true) {
					System.out.println("Enter StudentID:  \n to exit enter Q");
					String id = br.readLine();
					if(id.equals("q") || id.equals("Q")) {
						break;
					}
					Student currentStudent = db.allStudent().stream().filter(student -> student.getSystemId().equals(id)).findFirst().orElse(null);
					if(currentStudent !=null) {
						EnrollmentService.enrollStudent(currentStudent, currentEnrollment);
						System.out.println("Student: " + currentStudent.getSystemId() + " Enrolled on: " + currentCourse.getCourseName());
						db.saveEnrollments();
					}
					else {
						System.out.println("Unexistent Student ID or invalid format!");
					}
				}
			}
			else if(answer.equals("2")) {
				System.out.println("APPROVEMENT");
				EnrollmentService.approveStudents(currentEnrollment);
			}
			System.out.println("Do you want to close the Enrollment? y/n");
			String answer2 = br.readLine();
			if(answer2.equals("y")) {
				currentEnrollment.closeEnrollment();
				System.out.println("Enrollment: " + currentEnrollment.getCourse() + "is Closed!");
			}
		}
	}

//--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_-NEWS FOR MANAGERS-_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_	
	public static void newsManage() throws IOException {
		while(true) {
			System.out.println("Welcome to News Management!!! \n1>Publish Research \n2>Generate top Research \n3>Get all News \n4>Publish News \n5>Return to Main");
			String input = br.readLine();
			switch(input) {
			case "1": researchPublisher();
			case "2": System.out.println(NewsService.generateTopResearch());
			case "3": newsPrinter();
			case "4":
			case "5": break;
			default: System.out.println("Wrong format!");
			}
		}
	}
	
	public static void researchPublisher() {
		
	}
	
	public static void newsPrinter() {
		
	}
}

