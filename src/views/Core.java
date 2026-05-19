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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import config.DbContext;
import lang.LanguageManager;
import service.AccountType;
import model.Course;
import model.Employee;
import model.Enrollment;
import model.Gender;
import model.Manager;
import model.News;
import model.ResearchProject;
import model.ResearcherDecorator;
import model.Student;
import model.Teacher;
import model.TeacherTitle;
import model.User;


public class Core {
	private static User currentUser;
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static DbContext db = DbContext.getInstance();
	//--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_-RUN-_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_
	public static void run() throws IOException, ParseException {
		while(true) {
			LanguageManager.setLanguage("ru");
			System.out.println("\n========================================");
			System.out.println(LanguageManager.get("Welcome"));
			System.out.println("========================================");
			System.out.println("1>" + LanguageManager.get("sign_up"));
			System.out.println("2>" + LanguageManager.get("log_in"));
			System.out.println("0>" + LanguageManager.get("Exit"));
			System.out.print(LanguageManager.get("Your_choice")+": ");
			String answer = br.readLine().trim();
			currentUser = null;
			switch(answer) {
				case "1": currentUser = signup(); break;
				case "2": currentUser = auth(); break;
				case "0":
					return;
				default:
					System.out.println(LanguageManager.get("unexistent_option"));
					continue;
			}
			if(currentUser != null) {
				mainPage();
				System.out.println(LanguageManager.get("logged_out"));
			} else {
				System.out.println(LanguageManager.get("Invalid_pl"));
			}
		}
	}

	//--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_-LOGIN AND SIGNUP-_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_
	public static User auth() throws IOException {
		System.out.println(LanguageManager.get("enter_login"));
		String username = br.readLine();
		System.out.println(LanguageManager.get("enter_password"));
		String password = br.readLine();
		currentUser = AuthService.login(username, password);
		if(currentUser == null) System.out.println(LanguageManager.get("Invalid_pl"));
		return currentUser;
	}

	public static User signup() throws IOException, ParseException {
		List<User> users = DbContext.getInstance().allUsers();

		System.out.println(LanguageManager.get("enter_login"));
		String username = br.readLine();
		if(username.isBlank()) { System.out.println(LanguageManager.get("login_not_empty")); return null; }


		if(users.stream().anyMatch(u -> u.getLogin().equals(username))) {
			System.out.println(LanguageManager.get("login_taken"));
			return signup();
		}

		System.out.println(LanguageManager.get("enter_password"));
		String password = br.readLine();
		if(password.isBlank()) { System.out.println(LanguageManager.get("password_not_empty")); return null; }

		System.out.println(LanguageManager.get("ch_acc_type"));
		System.out.println(LanguageManager.get("acc_types"));
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
			default -> { System.out.println(LanguageManager.get("inv_acc_type")); return null; }
		}
		User currUser = AuthService.signUp(username,password,at);
		if(currUser instanceof Teacher) {
			System.out.println(LanguageManager.get("teacher_titles"));
			String TT = br.readLine();
			TeacherTitle tt = null;
			switch(TT) {
			case "1" -> tt = TeacherTitle.TUTOR;
			case "2" -> tt = TeacherTitle.LECTOR;
			case "3" -> tt = TeacherTitle.SENIOR_LECTOR;
			case "4" -> tt = TeacherTitle.PROFESSOR;
			default -> {System.out.println(LanguageManager.get("wrong_format"));break;}
			}
			System.out.println(tt);
			((Teacher) currUser).setTitle(tt);
			db.saveUsers();
		}
		return currUser;
	}
	

	//--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_-MAIN-_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_
	public static void mainPage() throws IOException {
		System.out.println(LanguageManager.get("welcome_caps") + Core.currentUser.getLogin().toUpperCase() + "!");
		if(currentUser instanceof Employee) {
			while(true) {
				System.out.println(LanguageManager.get("main_menu"));
				System.out.println("1>" + LanguageManager.get("messages"));
				System.out.println("2>" + LanguageManager.get("pro_menu"));
				System.out.println("3>" + LanguageManager.get("profile"));
				System.out.println("4>" + LanguageManager.get("res_menu"));
				System.out.println("0>" + LanguageManager.get("Logout"));
				System.out.print(LanguageManager.get("Your_choice"));
				String input = br.readLine().trim();
				switch(input) {
					case "1": messagesMenu(); break;
					case "2": professionalMenu(currentUser); break;
					case "3": profile(); break;
					case "4": researchMenu(currentUser); break;
					case "0": return;
					default: System.out.println(LanguageManager.get("unexistent_option"));
				}
			}
		} else if(currentUser instanceof Student) {
			studentMenu((Student) currentUser);
		}
	}
	//--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_-PROFILE-_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_
	public static void profile() throws IOException {
		while(true) {
			System.out.println(LanguageManager.get("profile_caps"));
			System.out.println(LanguageManager.get("login") + ": " + Core.currentUser.getLogin());
			System.out.println(LanguageManager.get("name") +"Name: " + Core.currentUser.getFirstName());
			System.out.println(LanguageManager.get("surname") +"Surname: " + Core.currentUser.getLastName());
			System.out.println(LanguageManager.get("birth_date") +"Birth date: " + Core.currentUser.getBirthday());
			System.out.println(LanguageManager.get("gender") +"Gender: " + Core.currentUser.getGender());
			System.out.println(LanguageManager.get("id") +"ID: " + Core.currentUser.getId());
			System.out.println(LanguageManager.get("enter_caps") + ": \n1>"+LanguageManager.get("settings")+"\n2>"+LanguageManager.get("Exit"));
			String in = br.readLine();
			switch(in) {
			case("1"):settings(); break;
			case("2"):return;
			default: System.out.println(LanguageManager.get("unexistent_option"));
			}
		}
		
	}
	
	private static void settings() throws IOException{
		while(true) {
			System.out.println(LanguageManager.get("set_offer"));
			System.out.println(" 1>" + LanguageManager.get("login")
					+ "\n 2>" + LanguageManager.get("name") 
					+ "\n 3>" + LanguageManager.get("surname")
					+ "\n 4>" + LanguageManager.get("password")
					+ "\n 5>" + LanguageManager.get("save_and_exit")
					+ "\n" + LanguageManager.get("Your_choice"));
			String in = br.readLine();
			switch(in) {
			case("1"): 
				System.out.println(LanguageManager.get("new_login")); 
				String nl = br.readLine();
				if(nl.isEmpty() || nl.isBlank()) {
					System.out.println(LanguageManager.get("login_not_empty"));
				}
				else {
					Core.currentUser.setLogin(nl);
				}
				break;
			case("2"):
				System.out.println(LanguageManager.get("new_name")); 
				String nn = br.readLine();
				if(nn.isEmpty() || nn.isBlank()) {
					System.out.println(LanguageManager.get("name_not_empty"));
				}
				else {
					Core.currentUser.setName(nn);
				}
				break;
			case("3"):
				System.out.println(LanguageManager.get("new_surname")); 
				String nln = br.readLine();
				if(nln.isEmpty() || nln.isBlank()) {
					System.out.println(LanguageManager.get("surname_not_empty"));
				}
				else {
					Core.currentUser.setSurname(nln);;
				}
				break;
			case("4"):
				System.out.println(LanguageManager.get("enter_password")); 
				String pc = br.readLine();
				if(Core.currentUser.getPassword().equals(pc)) {
					System.out.println(LanguageManager.get("new_password")); 
					String np = br.readLine();
					if(np.isEmpty() || np.isBlank()) {
						System.out.println(LanguageManager.get("password_not_empty"));
					}
					else {
						System.out.println(LanguageManager.get("repeat_password"));
						String npc = br.readLine();
						if(npc.equals(np)) {
							Core.currentUser.setPassword(np);
							System.out.println(LanguageManager.get("password_changed"));
						}
						else {
							System.out.println(LanguageManager.get("passwords_not_equal"));
						}
					}
				}
				else {
					System.out.println(LanguageManager.get("login"));
				}
				break;
			case("5"): 
				db.saveUsers();
				db.saveStudents();
				return;
			}
		}
	}
	
	//--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_-PROFESSIONAL MENU-_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_
	public static void professionalMenu(User u) throws IOException {

		if(u instanceof Manager) {
			while(true) {
				System.out.println(LanguageManager.get("manager_menu"));
				System.out.println("1>"+LanguageManager.get("manage_enrollments"));
				System.out.println("2>"+LanguageManager.get("manage_news"));
				System.out.println("3>"+LanguageManager.get("gen_academic_report"));
				System.out.println("4>"+LanguageManager.get("new_course"));
				System.out.println("5>"+LanguageManager.get("schedule"));
				System.out.println("0>"+LanguageManager.get("back"));
				System.out.print(LanguageManager.get("Your_choice"));
				String input = br.readLine().trim();
				switch(input) {
					case "1": enrollment(); break;
					case "2": newsManage(); break;
					case "3": service.ReportGenerationService.generateAcademicReport(); break;
					case "4": service.CourseManagementService.addCourseForRegistration(); break;
					case "5": managerScheduleMenu(new ScheduleCreatingService()); break;
					case "0": return;
					default: System.out.println(LanguageManager.get("unexistent_option"));
				}
			}
		} else if(u instanceof Teacher) {
			teacherMenu((Teacher) u);
		}
	}


	//--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_-TEACHER MENU-_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_
	public static void teacherMenu(Teacher teacher) throws IOException {
		while(true) {
			System.out.println(LanguageManager.get("teacher_menu"));
			System.out.println("1>"+LanguageManager.get("put_marks"));
			System.out.println("2>"+LanguageManager.get("schedule"));
			System.out.println("3>"+LanguageManager.get("research_menu"));
			System.out.println("4>"+LanguageManager.get("view_courses"));
			System.out.println("5>"+LanguageManager.get("view_students"));
			System.out.println("0>"+LanguageManager.get("back"));
			System.out.print(LanguageManager.get("Your_choice"));
			String input = br.readLine().trim();
			switch(input) {
				case "1": markPuttingMenu(teacher); break;
				case "2": teacherScheduleMenu(teacher); break;
				case "3": researchMenu(teacher); break;
				case "4": viewCourses();
				case "5": viewStudents();
				case "0": return;
				default: System.out.println(LanguageManager.get("unexistent_option"));
			}
		}
	}
	
	private static void viewCourses() {
		System.out.println(LanguageManager.get("cur_en_courses"));
		for(Enrollment en: db.allEnrollments()) {
			System.out.println(en.getCourse() + "\n");
		}
	}
	
	private static void viewStudents() {
		System.out.println(LanguageManager.get("cur_en_students"));
		for(Enrollment en: db.allEnrollments()) {
			System.out.println(en.getStudents() + "\n");
		}
	}

	//--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_-TEACHER SCHEDULE-_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_
	public static void teacherScheduleMenu(Teacher teacher) throws IOException {
		ScheduleCreatingService scs = new ScheduleCreatingService();
		System.out.println(LanguageManager.get("my_schedule"));
		if(teacher.getSchedule().isEmpty()) {
			System.out.println(LanguageManager.get("your_sc_is_mt"));
		} else {
			scs.printTeacherSchedule(teacher);
		}
	}
	//--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_-MANAGER SCHEDULE-_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_
	public static void managerScheduleMenu(ScheduleCreatingService scs) throws IOException {
		while(true) {
			System.out.println(LanguageManager.get("schedule_menu"));
			System.out.println("1>" + LanguageManager.get("build_ss"));
			System.out.println("2>" + LanguageManager.get("view_t_s"));
			System.out.println("0>" + LanguageManager.get("back"));
			System.out.print(LanguageManager.get("Your_choice"));
			String input = br.readLine().trim();
			switch(input) {
				case "1": buildScheduleMenu(scs); break;
				case "2": viewTeacherScheduleMenu(scs); break;
				case "0": return;
				default: System.out.println(LanguageManager.get("unexistent_option"));
			}
		}
	}
	//--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_-LOOK UP TEACHERS FOR SCHEDULE FOR MANAGERS-_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_
	public static void viewTeacherScheduleMenu(ScheduleCreatingService scs) throws IOException {
		System.out.print(LanguageManager.get("e_teacher_id"));
		String tid = br.readLine().trim();
		Teacher t = (Teacher) db.allUsers().stream()
				.filter(u -> u instanceof Teacher && u.getSystemId().equals(tid))
				.findFirst().orElse(null);
		if(t == null) {
			System.out.println(LanguageManager.get("t_not_found") + tid + "\".");
			return;
		}
		if(t.getSchedule().isEmpty()) {
			System.out.println(t.getFirstName() + " " + t.getLastName() + LanguageManager.get("hnly"));
		} else {
			scs.printTeacherSchedule(t);
		}
	}
	//--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_-TEACHER MARK PUTTING-_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_
	public static void markPuttingMenu(Teacher teacher) throws IOException {
		MarkPuttingService mps = new MarkPuttingService();
		List<Course> courses = db.allCourses();
		if(courses.isEmpty()) {
			System.out.println(LanguageManager.get("mark.no_courses"));
			return;
		}
		System.out.println(LanguageManager.get("mark.your_courses"));
		for(int i = 0; i < courses.size(); i++) {
			System.out.println((i+1) + "> " + courses.get(i).getCourseName());
		}
		System.out.println(LanguageManager.get("mark.enter_course"));
		String raw = br.readLine().trim();

		int choice;
		try { choice = Integer.parseInt(raw); }
		catch(NumberFormatException e) { System.out.println(LanguageManager.get("mark.invalid_input")); return; }

		if(choice == 0 || choice > courses.size()) return;
		Course selected = courses.get(choice - 1);
		mps.putMarksForCourse(teacher, selected);
	}

	//--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_-SCHEDULE BUILDING MENU-_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_
	public static void buildScheduleMenu(ScheduleCreatingService scs) throws IOException {
		// ── Step 1: Pick course ───────────────────────────────────────────────
		List<Course> courses = db.allCourses();
		if(courses.isEmpty()) {
			System.out.println(LanguageManager.get("schedule.no_courses"));
			return;
		}
		System.out.println(LanguageManager.get("schedule.select_course"));
		for(int i = 0; i < courses.size(); i++) {
			System.out.println("  " + (i+1) + "> " + courses.get(i).getCourseName());
		}
		System.out.print(LanguageManager.get("Your_choice"));
		int courseIdx;
		try { courseIdx = Integer.parseInt(br.readLine().trim()) - 1; }
		catch(NumberFormatException e) { System.out.println(LanguageManager.get("common.invalid_input")); return; }
		if(courseIdx < 0 || courseIdx >= courses.size()) { System.out.println(LanguageManager.get("common.out_of_range")); return; }
		Course course = courses.get(courseIdx);

		// ────────────────────────────────────────── Step 2: Pick teachers from a list ────────────────────────────────
		List<Teacher> teachers = new ArrayList<>();
		for(User u : db.allUsers()) {
			if(u instanceof Teacher) teachers.add((Teacher) u);
		}
		if(teachers.isEmpty()) {
			System.out.println(LanguageManager.get("schedule.no_teachers"));
			return;
		}
		System.out.println(LanguageManager.get("available_teachers"));
		for(int i = 0; i < teachers.size(); i++) {
			Teacher t = teachers.get(i);
			System.out.println("  " + (i+1) + "> " + t.getFirstName() + " " + t.getLastName()
					+ " [ID: " + t.getSystemId() + "] " + t.getTitle());
		}

		System.out.print(LanguageManager.get("schedule.select_lecture_teacher"));
		int ltIdx;
		try { ltIdx = Integer.parseInt(br.readLine().trim()) - 1; }
		catch(NumberFormatException e) { System.out.println(LanguageManager.get("common.invalid_input")); return; }
		if(ltIdx < 0 || ltIdx >= teachers.size()) { System.out.println(LanguageManager.get("common.out_of_range")); return; }
		Teacher lectureTeacher = teachers.get(ltIdx);
		if(lectureTeacher.getTitle()==TeacherTitle.TUTOR) {
			System.out.println(LanguageManager.get("schedule.tutor_practice_only"));
			return;
		}

		System.out.print(LanguageManager.get("schedule.select_practice_teacher"));
		String ptRaw = br.readLine().trim();
		Teacher practiceTeacher;
		if(ptRaw.isEmpty()) {
			practiceTeacher = lectureTeacher;
			System.out.println(LanguageManager.get("schedule.same_teacher")
		            + lectureTeacher.getFirstName()
		            + LanguageManager.get("schedule.same_teacher_suffix"));
		} else {
			int ptIdx;
			try { ptIdx = Integer.parseInt(ptRaw) - 1; }
			catch(NumberFormatException e) { System.out.println(LanguageManager.get("common.invalid_input")); return; }
			if(ptIdx < 0 || ptIdx >= teachers.size()) { System.out.println(LanguageManager.get("common.out_of_range")); return; }
			practiceTeacher = teachers.get(ptIdx);
		}

		// ── Step 3: Number of weeks ───────────────────────────────────────────
		System.out.print("schedule.how_many_weeks");
		int weeks;
		try {
			weeks = Integer.parseInt(br.readLine().trim());
			if(weeks < 1 || weeks > 16) { System.out.println("schedule.weeks_range"); return; }
		} catch(NumberFormatException e) { System.out.println(LanguageManager.get("common.invalid_input")); return; }

		// ── Step 4: Lecture day & time ────────────────────────────────────────
		System.out.println( "\n" + LanguageManager.get("schedule.lecture_slot"));
		int lectureDow = pickDayOfWeek();
		if(lectureDow == -1) return;
		int lectureHour = pickHour();
		if(lectureHour == -1) return;

		// ── Step 5: Practice day & time (must be same day later OR later day) ─
		System.out.println("\n" + LanguageManager.get("schedule.practice_slot"));
		int practiceDow = pickDayOfWeek();
		if(practiceDow == -1) return;
		int practiceHour = pickHour();
		if(practiceHour == -1) return;

		// Validate: practice must come strictly after lecture
		boolean sameDay = (lectureDow == practiceDow);
		if(sameDay && practiceHour <= lectureHour) {
			System.out.println(LanguageManager.get("schedule.practice_after_lecture"));
			return;
		}
		if(practiceDow < lectureDow) {
			System.out.println(LanguageManager.get("schedule.practice_day_invalid"));
			return;
		}

		scs.buildSemesterSchedule(course, lectureTeacher, practiceTeacher,
				new Date(), weeks, lectureDow, lectureHour, practiceDow, practiceHour);
		db.saveUsers();
		db.saveCourses();
		db.saveEnrollments();
		db.saveStudents();
	}

	/** Prompts the manager to pick a weekday. Returns Calendar constant or -1 on bad input. */
	private static int pickDayOfWeek() throws IOException {
		System.out.println(LanguageManager.get("days_of_week"));
		System.out.print(LanguageManager.get("day"));
		switch(br.readLine().trim()) {
			case "1": return Calendar.MONDAY;
			case "2": return Calendar.TUESDAY;
			case "3": return Calendar.WEDNESDAY;
			case "4": return Calendar.THURSDAY;
			case "5": return Calendar.FRIDAY;
			default: System.out.println(LanguageManager.get("common.invalid_input")); return -1;
		}
	}


	/** Prompts the manager to pick an hour (8-18). Returns the hour or -1 on bad input. */
	private static int pickHour() throws IOException {
		System.out.print(LanguageManager.get("hour_range"));
		try {
			int hour = Integer.parseInt(br.readLine().trim());
			if(hour < 8 || hour > 18) { System.out.println(LanguageManager.get("mush_hour")); return -1; }
			return hour;
		} catch(NumberFormatException e) { System.out.println(LanguageManager.get("invalid_hour")); return -1; }
	}



	//--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_-MENU FOR RESEARCHERS-_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_
	public static void researchMenu(User user) throws IOException {
		ResearcherDecorator researcher = null;
		for(User u : db.allUsers()) {
			if(u instanceof ResearcherDecorator && ((ResearcherDecorator)u).getId() == user.getId()) {
				researcher = (ResearcherDecorator) u;
				break;
			}
		}
		if(researcher == null) {
			System.out.println(LanguageManager.get("research.not_registered"));
			return;
		}
		final ResearcherDecorator r = researcher;
		while(true) {
			System.out.println("\n=== "
		            + LanguageManager.get("research.menu_title")
		            + " ===");
			System.out.println("1> " + LanguageManager.get("research.publish_paper"));
			System.out.println("2> " + LanguageManager.get("research.print_by_citations"));
			System.out.println("3> " + LanguageManager.get("research.print_by_date"));
			System.out.println("4> " + LanguageManager.get("research.print_by_pages"));
			System.out.println("5> " + LanguageManager.get("research.view_hindex"));
			System.out.println("6> " + LanguageManager.get("research.close_project"));
			System.out.println("0> " + LanguageManager.get("back"));
			System.out.print(LanguageManager.get("Your_choice"));
			String input = br.readLine().trim();
			switch(input) {
				case "1":
					System.out.print( LanguageManager.get("research.title")); String title = br.readLine();
					if(title.isBlank()) { System.out.println(LanguageManager.get("research.title_empty")); break; }
					System.out.print(LanguageManager.get("research.content_summary")); String content = br.readLine();
					System.out.print(LanguageManager.get("research.pages"));
					int pages;
					try { pages = Integer.parseInt(br.readLine().trim()); }
					catch(NumberFormatException e) { System.out.println(LanguageManager.get("research.invalid_pages")); break; }
					if(pages <= 0) { System.out.println(LanguageManager.get("research.pages_positive")); break; }
					r.conductResearch(content, title, pages);
					break;
				case "2":
					if(r.getPapers().isEmpty()) { System.out.println(LanguageManager.get("research.no_papers")); break; }
					r.printPapers(new ByCitations()); break;
				case "3":
					if(r.getPapers().isEmpty()) { System.out.println(LanguageManager.get("research.no_papers")); break; }
					r.printPapers(new ByDate()); break;
				case "4":
					if(r.getPapers().isEmpty()) { System.out.println(LanguageManager.get("research.no_papers")); break; }
					r.printPapers(new ByPages()); break;
				case "5":
					if(r.getPapers().isEmpty()) { System.out.println(LanguageManager.get("research.hindex_zero")); break; }
					System.out.printf(LanguageManager.get("research.hindex"), r.calculateH()); break;
				case "6":
					if(r.getPapers().isEmpty()) { System.out.println( LanguageManager.get("research.no_loose_papers")); break; }
					System.out.print(LanguageManager.get("research.project_topic")); String topic = br.readLine();
					if(topic.isBlank()) { System.out.println(LanguageManager.get("research.topic_empty")); break; }
					r.closeProject(topic);
					break;
				case "0": return;
				default: System.out.println(LanguageManager.get("research.invalid_option"));
			}
		}
	}
   
	//--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_-MENU FOR STUDENTS-_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_
	public static void studentMenu(Student student) throws IOException {
		while(true) {
			System.out.println("\n=== "
		            + LanguageManager.get("student.menu_title")
		            + " ===");
			System.out.println("1> " + LanguageManager.get("student.view_courses"));
			System.out.println("2> " + LanguageManager.get("student.view_transcript"));
			System.out.println("3> " + LanguageManager.get("student.view_schedule"));
			System.out.println("4> " + LanguageManager.get("student.register_courses"));
			System.out.println("5> " + LanguageManager.get("student.research_menu"));
			System.out.println("0> " + LanguageManager.get("common.logout"));
			System.out.print(LanguageManager.get("Your_choice"));
			String input = br.readLine().trim();
			switch(input) {
				case "1":
					List<Enrollment> myEnrollments = new ArrayList<>();
					for(Enrollment en : db.allEnrollments()) {
						if(en.getStudents().containsKey(student)) myEnrollments.add(en);
					}
					if(myEnrollments.isEmpty()) {
						System.out.println(LanguageManager.get("student.no_enrollments"));
					} else {
						for(Enrollment en : myEnrollments) {
							boolean approved = en.getStudents().get(student);
							System.out.println("  " + en.getCourse().getCourseName()
								+ " [" + (approved ? LanguageManager.get(
	                                    "student.approved"
		                                  ) : LanguageManager.get(
		                                          "student.pending"
		                                          )) + "]");
						}
					}
					break;
				case "2":
					if(student.getTranscript() == null) {
						System.out.println(LanguageManager.get(
	                            "student.no_transcript"
		                        ));
					} else {
						System.out.println(student.getTranscript());
					}
					break;
				case "3":
					if(student.getSchedule() == null || student.getSchedule().isEmpty()) {
						System.out.println(LanguageManager.get(
	                            "student.empty_schedule"
		                        ));
					} else {
						student.getSchedule().forEach(se -> System.out.println("  " + se.getDays() + "): " + se.getCourse()));
					}
					break;
				case "4": registerForCourse();break;
				case "5": researchMenu(currentUser); break;
				case "0": return;
				default: System.out.println(LanguageManager.get("student.invalid_option"));
			}
		}
	}

	//--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_-MESSAGES MENU-_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_--_-_
	public static void messagesMenu() throws IOException {
		MessageSendingService msgService = new MessageSendingService();
		System.out.println(LanguageManager.get("message_menu"));
		System.out.println(LanguageManager.get("write"));
		System.out.println(LanguageManager.get("read"));
		System.out.print(LanguageManager.get("Your_choice"));
		String ans = br.readLine();

		if (ans.equals("1")) {
			msgService.sendMessage(Core.currentUser);
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
			System.out.println("What do you want to do? \n1>Add Student to the enrollment \n2>Approve registration for students of the enrollment \n3>Assign teacher to the course");
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
			else if(answer.equals("3")) {
				List<Teacher> teachers = db.allUsers().stream().filter(user -> user instanceof Teacher).map(user -> (Teacher) user).toList();
				System.out.println("Available teachers");
				for(Teacher t: teachers) {
					System.out.println(t.getFirstName() + " " + t.getLastName() + " [ " + t.getSystemId() + " ]");
				}
				System.out.println("Enter teacher ID: ");
				String id = br.readLine();
				Teacher t = teachers.stream().filter(teacher -> teacher.getSystemId().equals(id)).findFirst().orElse(null);
				if(t==null) {
					System.out.println("Such id does not exist");
				}
				else {
					currentEnrollment.addTeacher(t);
					System.out.println("Teacher: " + t + "added to " + currentEnrollment.getCourse().getCourseName());
					db.saveEnrollments();
				}
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
	
	
	public static void registerForCourse() throws IOException {
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
				System.out.println("This enrollment is closed!");
			}
			else {
				Enrollment currentEnrollment = EnrollmentService.getEnrollment(currentCourse);
				System.out.println("Enrollment is Found!");
				EnrollmentService.enrollStudent((Student) Core.currentUser, currentEnrollment);
				System.out.println("You : " + Core.currentUser.getSystemId() + " Enrolled on: " + currentCourse.getCourseName());
				db.saveEnrollments();
			}
		}
	}
}