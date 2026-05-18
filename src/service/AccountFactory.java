package service;
import model.User;
import model.Student;
import model.MasterStudent;
import model.PhDStudent;
import config.DbContext;
import model.Admin;
import model.Teacher;
import model.Manager;
import model.TechSupportSpecialist;

public class AccountFactory {
	private static DbContext db = DbContext.getInstance();
	public static User createAccount(AccountType type, String login, String password){
		boolean exist = db.allUsers().stream().anyMatch(u->u.getLogin().equals(login));
		if(exist){
			System.out.println("Login is taken");
			return null;
		}
		User newUser = switch(type) {
			case AccountType.STUDENT -> new Student(login, password);
			case AccountType.MASTER -> new MasterStudent(login, password);
			case AccountType.PHD -> new PhDStudent(login,password);
			case AccountType.TEACHER -> new Teacher(login,password);
			case AccountType.ADMIN -> new Admin(login,password);
			case AccountType.MANAGER -> new Manager(login,password);
			case AccountType.TECH -> new TechSupportSpecialist(login,password);
			default -> throw new IllegalArgumentException("Unexpected value: " + type);
		};
		return newUser;
	}
}
