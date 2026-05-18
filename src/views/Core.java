package views;
import service.AuthService;
import service.ByCitations;
import service.ByDate;
import service.ByPages;
import service.EnrollmentService;
import service.MarkPuttingService;
import service.MessageSendingService; // ДОБАВЛЕНО АЗИЗОЙ
import service.NewsService;
import service.ScheduleCreatingService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import config.DbContext;
import service.AccountType;
import model.Course;
import model.Employee;
import model.Enrollment;
import model.Manager;
import model.News;
import model.ResearchProject;
import model.ResearcherDecorator;
import model.Student;
import model.Teacher;
import model.User;

public class Core {
	private static User currentUser;
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static DbContext db = DbContext.getInstance();

	public static void run() throws IOException {
		while(true) {
			System.out.println("\n========================================");
			System.out.println("   Welcome to University System!");
			System.out.println("========================================");
			System.out.println("1> Sign up");
			System.out.println("2> Log in");
			System.out.println("0> Exit system");
			System.out.print("Your choice: ");
			String answer = br.readLine().trim();
			currentUser = null;
			switch(answer) {
				case "1": currentUser = signup(); break;
				case "2": currentUser = auth(); break;
				case "0":
					return;
				default:
					System.out.println("Unexistent option.");
					continue;
			}
			if(currentUser != null) {
				mainPage();
				System.out.println("You have been logged out.");
			} else {
				System.out.println("Username or password are not valid!");
			}
		}
	}

	//--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_-LOGIN AND SIGNUP-_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_
	public static User auth() throws IOException {
		System.out.println("Enter your username!");
		String username = br.readLine();
		System.out.println("Enter your password");
		String password = br.readLine();
		currentUser = AuthService.login(username, password);
		if(currentUser == null) System.out.println("Invalid username or password.");
		return currentUser;
	}

	public static User signup() throws IOException {
		List<User> users = DbContext.getInstance().allUsers();

		System.out.println("Enter your username!");
		String username = br.readLine();
		if(username.isBlank()) { System.out.println("Username cannot be empty."); return null; }


		if(users.stream().anyMatch(u -> u.getLogin().equals(username))) {
			System.out.println("This username already exists.");
			return signup();
		}

		System.out.println("Enter your password");
		String password = br.readLine();
		if(password.isBlank()) { System.out.println("Password cannot be empty."); return null; }

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
			default -> { System.out.println("Invalid account type."); return null; }
		}
		
		User currUser = AuthService.signUp(username,password,at);
		return currUser;
	}

	public static void mainPage() throws IOException {
		System.out.println("\nWELCOME, " + currentUser.getName().toUpperCase() + "!");
		if(currentUser instanceof Employee) {
			while(true) {
				System.out.println("\n=== Main Menu ===");
				System.out.println("1> Messages");
				System.out.println("2> Professional menu");
				System.out.println("0> Logout");
				System.out.print("Your choice: ");
				String input = br.readLine().trim();
				switch(input) {
					case "1": messagesMenu(); break;
					case "2": professionalMenu(currentUser); break;
					case "0": return;
					default: System.out.println("Unexistent option");
				}
			}
		} else if(currentUser instanceof Student) {
			studentMenu((Student) currentUser);
		}
	}

	public static void professionalMenu(User u) throws IOException {
		if(u instanceof Manager) {
			while(true) {
				System.out.println("\n=== Manager Menu ===");
				System.out.println("1> Manage enrollments");
				System.out.println("2> Manage news");
				System.out.println("3> Generate Academic Report");
				System.out.println("4> Create New Course");
				System.out.println("5> Schedule");
				System.out.println("0> Back");
				System.out.print("Your choice: ");
				String input = br.readLine().trim();
				switch(input) {
					case "1": enrollment(); break;
					case "2": newsManage(); break;
					case "3": service.ReportGenerationService.generateAcademicReport(); break;
					case "4": service.CourseManagementService.addCourseForRegistration(); break;
					case "5": managerScheduleMenu(new ScheduleCreatingService()); break;
					case "0": return;
					default: System.out.println("Unexistent option");
				}
			}
		} else if(u instanceof Teacher) {
			teacherMenu((Teacher) u);
		}
	}


   // Teacher menu
	public static void teacherMenu(Teacher teacher) throws IOException {
		while(true) {
			System.out.println("\n=== Teacher Menu ===");
			System.out.println("1> Put marks for a course");
			System.out.println("2> View my schedule");
			System.out.println("3> Research menu");
			System.out.println("0> Back");
			System.out.print("Your choice: ");
			String input = br.readLine().trim();
			switch(input) {
				case "1": markPuttingMenu(teacher); break;
				case "2": teacherScheduleMenu(teacher); break;
				case "3": researchMenu(teacher); break;
				case "0": return;
				default: System.out.println("Invalid option. Please enter 1, 2, 3, or 0.");
			}
		}
	}

	public static void teacherScheduleMenu(Teacher teacher) throws IOException {
		ScheduleCreatingService scs = new ScheduleCreatingService();
		System.out.println("\n=== My Schedule ===");
		if(teacher.getSchedule().isEmpty()) {
			System.out.println("Your schedule is empty. A Manager needs to assign lessons to your courses first.");
		} else {
			scs.printTeacherSchedule(teacher);
		}
	}

	public static void managerScheduleMenu(ScheduleCreatingService scs) throws IOException {
		while(true) {
			System.out.println("\n=== Schedule Menu ===");
			System.out.println("1> Build semester schedule for a course");
			System.out.println("2> View a teacher's schedule");
			System.out.println("0> Back");
			System.out.print("Your choice: ");
			String input = br.readLine().trim();
			switch(input) {
				case "1": buildScheduleMenu(scs); break;
				case "2": viewTeacherScheduleMenu(scs); break;
				case "0": return;
				default: System.out.println("Invalid option. Please enter 1, 2, or 0.");
			}
		}
	}

	public static void viewTeacherScheduleMenu(ScheduleCreatingService scs) throws IOException {
		System.out.print("Enter teacher ID: ");
		String tid = br.readLine().trim();
		Teacher t = (Teacher) db.allUsers().stream()
				.filter(u -> u instanceof Teacher && u.getSystemId().equals(tid))
				.findFirst().orElse(null);
		if(t == null) {
			System.out.println("No Teacher found with ID \"" + tid + "\".");
			return;
		}
		if(t.getSchedule().isEmpty()) {
			System.out.println(t.getFirstName() + " " + t.getLastName() + " has no scheduled lessons yet.");
		} else {
			scs.printTeacherSchedule(t);
		}
	}

	public static void markPuttingMenu(Teacher teacher) throws IOException {
		MarkPuttingService mps = new MarkPuttingService();
		List<Course> courses = db.allCourses();
		if(courses.isEmpty()) {
			System.out.println("No courses exist in the system yet.");
			return;
		}
		System.out.println("Your courses:");
		for(int i = 0; i < courses.size(); i++) {
			System.out.println((i+1) + "> " + courses.get(i).getCourseName());
		}
		System.out.println("Enter course number (or 0 to cancel):");
		String raw = br.readLine().trim();

		int choice;
		try { choice = Integer.parseInt(raw); }
		catch(NumberFormatException e) { System.out.println("Invalid input."); return; }

		if(choice == 0 || choice > courses.size()) return;
		Course selected = courses.get(choice - 1);
		mps.putMarksForCourse(teacher, selected);
	}


	public static void buildScheduleMenu(ScheduleCreatingService scs) throws IOException {
		// ── Step 1: Pick course ───────────────────────────────────────────────
		List<Course> courses = db.allCourses();
		if(courses.isEmpty()) {
			System.out.println("No courses in the system yet. Add a course first.");
			return;
		}
		System.out.println("\nSelect course:");
		for(int i = 0; i < courses.size(); i++) {
			System.out.println("  " + (i+1) + "> " + courses.get(i).getCourseName());
		}
		System.out.print("Your choice: ");
		int courseIdx;
		try { courseIdx = Integer.parseInt(br.readLine().trim()) - 1; }
		catch(NumberFormatException e) { System.out.println("Invalid input."); return; }
		if(courseIdx < 0 || courseIdx >= courses.size()) { System.out.println("Out of range."); return; }
		Course course = courses.get(courseIdx);

		// ── Step 2: Pick teachers from a list ────────────────────────────────
		List<Teacher> teachers = new ArrayList<>();
		for(User u : db.allUsers()) {
			if(u instanceof Teacher) teachers.add((Teacher) u);
		}
		if(teachers.isEmpty()) {
			System.out.println("No teachers in the system yet.");
			return;
		}
		System.out.println("\nAvailable teachers:");
		for(int i = 0; i < teachers.size(); i++) {
			Teacher t = teachers.get(i);
			System.out.println("  " + (i+1) + "> " + t.getFirstName() + " " + t.getLastName()
					+ " [ID: " + t.getSystemId() + "]");
		}

		System.out.print("Select lecture teacher (number): ");
		int ltIdx;
		try { ltIdx = Integer.parseInt(br.readLine().trim()) - 1; }
		catch(NumberFormatException e) { System.out.println("Invalid input."); return; }
		if(ltIdx < 0 || ltIdx >= teachers.size()) { System.out.println("Out of range."); return; }
		Teacher lectureTeacher = teachers.get(ltIdx);

		System.out.print("Select practice teacher (number, or press Enter to use same): ");
		String ptRaw = br.readLine().trim();
		Teacher practiceTeacher;
		if(ptRaw.isEmpty()) {
			practiceTeacher = lectureTeacher;
			System.out.println("Using " + lectureTeacher.getFirstName() + " for practice as well.");
		} else {
			int ptIdx;
			try { ptIdx = Integer.parseInt(ptRaw) - 1; }
			catch(NumberFormatException e) { System.out.println("Invalid input."); return; }
			if(ptIdx < 0 || ptIdx >= teachers.size()) { System.out.println("Out of range."); return; }
			practiceTeacher = teachers.get(ptIdx);
		}

		// ── Step 3: Number of weeks ───────────────────────────────────────────
		System.out.print("How many weeks? (1-16): ");
		int weeks;
		try {
			weeks = Integer.parseInt(br.readLine().trim());
			if(weeks < 1 || weeks > 16) { System.out.println("Must be 1-16."); return; }
		} catch(NumberFormatException e) { System.out.println("Invalid input."); return; }

		// ── Step 4: Lecture day & time ────────────────────────────────────────
		System.out.println("\n-- Lecture slot --");
		int lectureDow = pickDayOfWeek();
		if(lectureDow == -1) return;
		int lectureHour = pickHour();
		if(lectureHour == -1) return;

		// ── Step 5: Practice day & time (must be same day later OR later day) ─
		System.out.println("\n-- Practice slot (must be after lecture) --");
		int practiceDow = pickDayOfWeek();
		if(practiceDow == -1) return;
		int practiceHour = pickHour();
		if(practiceHour == -1) return;

		// Validate: practice must come strictly after lecture
		boolean sameDay = (lectureDow == practiceDow);
		if(sameDay && practiceHour <= lectureHour) {
			System.out.println("Invalid: practice must start after the lecture ends on the same day.");
			return;
		}
		if(practiceDow < lectureDow) {
			System.out.println("Invalid: practice day cannot be earlier in the week than lecture day.");
			return;
		}

		scs.buildSemesterSchedule(course, lectureTeacher, practiceTeacher,
				new Date(), weeks, lectureDow, lectureHour, practiceDow, practiceHour);
	}


	/** Prompts the manager to pick a weekday. Returns Calendar constant or -1 on bad input. */
	private static int pickDayOfWeek() throws IOException {
		System.out.println("  1> Monday  2> Tuesday  3> Wednesday  4> Thursday  5> Friday");
		System.out.print("  Day: ");
		switch(br.readLine().trim()) {
			case "1": return Calendar.MONDAY;
			case "2": return Calendar.TUESDAY;
			case "3": return Calendar.WEDNESDAY;
			case "4": return Calendar.THURSDAY;
			case "5": return Calendar.FRIDAY;
			default: System.out.println("Invalid day."); return -1;
		}
	}


	/** Prompts the manager to pick an hour (8-18). Returns the hour or -1 on bad input. */
	private static int pickHour() throws IOException {
		System.out.print("  Start hour (8-18): ");
		try {
			int hour = Integer.parseInt(br.readLine().trim());
			if(hour < 8 || hour > 18) { System.out.println("Must be between 8 and 18."); return -1; }
			return hour;
		} catch(NumberFormatException e) { System.out.println("Invalid hour."); return -1; }
	}



    // Research menu
	public static void researchMenu(User user) throws IOException {
		ResearcherDecorator researcher = null;
		for(User u : db.allUsers()) {
			if(u instanceof ResearcherDecorator && ((ResearcherDecorator)u).getId() == user.getId()) {
				researcher = (ResearcherDecorator) u;
				break;
			}
		}
		if(researcher == null) {
			System.out.println("You are not registered as a Researcher. Contact an Admin to get Researcher status.");
			return;
		}
		final ResearcherDecorator r = researcher;
		while(true) {
			System.out.println("\n=== Research Menu ===");
			System.out.println("1> Publish a paper");
			System.out.println("2> Print my papers (by citations)");
			System.out.println("3> Print my papers (by date)");
			System.out.println("4> Print my papers (by pages)");
			System.out.println("5> View h-index");
			System.out.println("6> Close & submit project");
			System.out.println("0> Back");
			System.out.print("Your choice: ");
			String input = br.readLine().trim();
			switch(input) {
				case "1":
					System.out.print("Title: "); String title = br.readLine();
					if(title.isBlank()) { System.out.println("Title cannot be empty."); break; }
					System.out.print("Content summary: "); String content = br.readLine();
					System.out.print("Pages: ");
					int pages;
					try { pages = Integer.parseInt(br.readLine().trim()); }
					catch(NumberFormatException e) { System.out.println("Invalid page count."); break; }
					if(pages <= 0) { System.out.println("Pages must be greater than 0."); break; }
					r.conductResearch(content, title, pages);
					break;
				case "2":
					if(r.getPapers().isEmpty()) { System.out.println("You have no published papers yet."); break; }
					r.printPapers(new ByCitations()); break;
				case "3":
					if(r.getPapers().isEmpty()) { System.out.println("You have no published papers yet."); break; }
					r.printPapers(new ByDate()); break;
				case "4":
					if(r.getPapers().isEmpty()) { System.out.println("You have no published papers yet."); break; }
					r.printPapers(new ByPages()); break;
				case "5":
					if(r.getPapers().isEmpty()) { System.out.println("You have no papers — h-index is 0."); break; }
					System.out.printf("Your h-index: %.0f%n", r.calculateH()); break;
				case "6":
					if(r.getPapers().isEmpty()) { System.out.println("You have no loose papers to form a project from."); break; }
					System.out.print("Project topic: "); String topic = br.readLine();
					if(topic.isBlank()) { System.out.println("Topic cannot be empty."); break; }
					r.closeProject(topic);
					break;
				case "0": return;
				default: System.out.println("Invalid option. Please enter 1–6 or 0.");
			}
		}
	}
   
    // Student menu
	public static void studentMenu(Student student) throws IOException {
		while(true) {
			System.out.println("\n=== Student Menu ===");
			System.out.println("1> View my courses");
			System.out.println("2> View my marks / transcript");
			System.out.println("3> View my schedule");
			System.out.println("0> Logout");
			System.out.print("Your choice: ");
			String input = br.readLine().trim();
			switch(input) {
				case "1":
					List<Enrollment> myEnrollments = new ArrayList<>();
					for(Enrollment en : db.allEnrollments()) {
						if(en.getStudents().containsKey(student)) myEnrollments.add(en);
					}
					if(myEnrollments.isEmpty()) {
						System.out.println("You are not enrolled in any courses yet. Ask a Manager to enroll you.");
					} else {
						for(Enrollment en : myEnrollments) {
							boolean approved = en.getStudents().get(student);
							System.out.println("  " + en.getCourse().getCourseName()
								+ " [" + (approved ? "approved" : "pending approval") + "]");
						}
					}
					break;
				case "2":
					if(student.getTranscript() == null) {
						System.out.println("No transcript found. Marks will appear here once a Teacher enters them.");
					} else {
						System.out.println(student.getTranscript());
					}
					break;
				case "3":
					if(student.getSchedule() == null || student.getSchedule().isEmpty()) {
						System.out.println("Your schedule is empty. It will populate once a Manager builds the semester schedule.");
					} else {
						student.getSchedule().forEach(se -> System.out.println("  " + se));
					}
					break;
				case "0": return;
				default: System.out.println("Unexistent option");
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