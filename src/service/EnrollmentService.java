package service;
import model.Course;
import model.Enrollment;

import java.io.*;
import java.util.*;

import config.DbContext;
import model.Student;
import model.Teacher;


public class EnrollmentService {
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void enrollStudent(Student s,Enrollment en) {
        en.addStudent(s);
    }

    public static void removeStudent( Student s, Enrollment en) {
        en.removeStudent(s);
    }

    public static void seeTeachers(Enrollment en) {
        for(Teacher t : en.getInstructors()) {
        	System.out.println(t);
        }
    }
    
    public static Enrollment getEnrollment(Course c) {
    	return DbContext.getInstance().allEnrollments().stream().filter(en -> en.getCourse().equals(c) && en.getAvailability()==true).findFirst().orElse(null);
    }
    
    public static void approveStudents(Enrollment en) throws NumberFormatException, IOException {
    	HashMap<Student,Boolean> app = en.getStudents();
    	System.out.println("Enter to: Approve - 1, Deny - 2");
    	for(Student s: app.keySet()) {
    		System.out.println(s.getId() + s.getFirstName());
    		Boolean b = false;
    		if(Integer.parseInt(br.readLine()) == 1) {
    			b = true;
    		}
    		app.replace(s, b);
    	}
    }
    
    public static Enrollment createEnrollment(Course c) {
    	Enrollment en = new Enrollment(c);
    	DbContext.getInstance().addEnrollment(en);
    	return en;
    }
}