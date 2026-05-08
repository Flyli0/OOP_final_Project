package app;

import model.Course;
import model.Employee;
import model.News;
import views.Core;
import model.Student;

import java.io.IOException;

import config.DbContext;

public class Test {
  public static void main(String[] args) throws IOException {
/*	
	  Student s1 = new Student("w","www",0);
	  Student s2 = new Student("r","rrr",0);
	  Student s3 = new Student("k","kkk",0);2
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
	  
	  News n1 = new News("Cool","Aliens ARE REALLL", "Schizophrenia is bad, but aliens are funny papapapap", s1);
	  News n2 = new News("Not Bad","Why Kniga is goood", "Knigi ochen horosho vliyayut na vash slovarnyy zapas", s2);
	  News n3 = new News("Emergency","Disaster in the Cafe", "Donuts are sold out, i wanted to buy one :(", s3);
	  DbContext.getInstance().addNews(n1);
	  DbContext.getInstance().addNews(n2);
	  DbContext.getInstance().addNews(n3);
*/	  
	  
	  System.out.println("COURSES" + DbContext.getInstance().loadCourses());
	  System.out.println("USERS"+DbContext.getInstance().loadUsers());
	  System.out.println("STUDENTS"+DbContext.getInstance().loadStudents());
	  System.out.println("NEWS"+DbContext.getInstance().loadNews());
	  Core.run();
  }
}