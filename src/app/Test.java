package app;

import model.Course;
import model.Employee;
import views.Core;
import model.Student;

import java.io.IOException;

import config.DbContext;

public class Test {
  public static void main(String[] args) throws IOException {
	  /*Student s1 = new Student("w","www",0);
	  Student s2 = new Student("r","rrr",0);
	  Student s3 = new Student("k","kkk",0);
	  Student s4 = new Student("l","lll",0);
	  Student s5 = new Student("a","aaa",0);
	  
	  DbContext.getInstance().addStudent(s1);
	  DbContext.getInstance().addStudent(s2);
	  DbContext.getInstance().addStudent(s3);
	  DbContext.getInstance().addStudent(s4);
	  DbContext.getInstance().addStudent(s5);
	  Course c1 = new Course("ADS",5);
	  Course c2 = new Course("Physics 2",3);
	  Course c3 = new Course("English C1",3);
	  DbContext.getInstance().addCourse(c1);
	  DbContext.getInstance().addCourse(c2);
	  DbContext.getInstance().addCourse(c3);
	  */
	  
	  
	  
	  System.out.println("COURSES" + DbContext.getInstance().loadCourses());
	  System.out.println("USERS"+DbContext.getInstance().loadUsers());
	  System.out.println("STUDENTS"+DbContext.getInstance().loadStudents());
	  Core.run();
  }
}