package views;
import service.AuthService;
import service.EnrollmentService;
import service.MessageSendingService; // ДОБАВЛЕНО АЗИЗОЙ
import service.NewsService;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import config.DbContext;
import service.AccountType;
import model.Course;
import model.Employee;
import model.Enrollment;
import model.Manager;
import model.News;
import model.ResearchProject;
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

	public static void mainPage() throws IOException {
		System.out.println("WELCOME!!!");
		//System.out.println(db.loadUsers());
		//System.out.println(db.loadEnrollments());
		if(currentUser instanceof Employee){
			System.out.println("Hello! \n choose your move: \n1>Post and Messages \n2>Professional menu");
			String input = br.readLine();
			switch(input) {
			case "1": messagesMenu(); break;
			case "2": professionalMenu(currentUser); break;
			default: System.out.println("Wrong format try again!");
			}
		}
	}
	
	public static void professionalMenu(User u) throws IOException {
		if(u instanceof Manager) {
			System.out.println("Hello Manager! \n choose your move: \n1>Manage enrollments \n2>Manage news");
			String input = br.readLine();
			switch(input) {
			case "1": enrollment(); break;
			case "2": newsManage(); break;
			default: System.out.println("Wrong format try again!");
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
			case "1": researchPublisher(); break;
			case "2": System.out.println(NewsService.generateTopResearch()); break;
			case "3": newsPrinter(); break;
			case "4": publishNews(); break;
			case "5": return;
			default: System.out.println("Wrong format!");
			}
		}
	}
	
	public static void researchPublisher() throws IOException {
		List<ResearchProject> temp = DbContext.getInstance().allPendingProjects();
		System.out.println("List of all pending projects: ");
		for(ResearchProject rp: temp) {
			System.out.println(rp.getTopic() + " : " + rp.getParticipants());
		}
		while(true) {
			System.out.println("Which project do you want to publish? (Enter topic) \nTo exit enter Q");
			String input = br.readLine();
			if(input.equals("Q".toUpperCase())) {
				break;
			}
			ResearchProject rp = DbContext.getInstance().allPendingProjects().stream().filter(rpe->rpe.getTopic().equals(input)).findFirst().orElse(null);
			if(rp == null) {
				System.out.println("Unexistent project!");
			}
			else {
				NewsService.publishResearch(rp);
				DbContext.getInstance().removePendingProject(rp);
				System.out.println("Research: " + rp.getTopic() + " is Published!!!");
			}
		}
	}
	
	public static void newsPrinter() {
		for(News n: DbContext.getInstance().allNews()) {
			System.out.println(n);
		}
	}
	
	public static void publishNews() throws IOException{
		String header = ""; String topic = ""; String content = "";
		System.out.println("New news :D");
		System.out.println("Enter Topic: ");
		topic = br.readLine();
		System.out.println("Enter Header");
		header = br.readLine();
		System.out.println("Enter Content");
		content = br.readLine();
		News n = NewsService.publishNews(header, topic, content, currentUser); 
		System.out.println("News: " + n.getId() + "is publised!!!");
	}
}

