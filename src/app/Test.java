package app;

import model.Course;
import model.Employee;
import model.News;
import model.ResearchProject;
import views.Core;
import model.Student;
import model.ResearcherDecorator;

import java.io.IOException;

import config.DbContext;

public class Test {
  public static void main(String[] args) throws IOException {
	/*	
	  Student s1 = new Student("w","www",0);
	  
	  ResearcherDecorator rs1 = new ResearcherDecorator(s1);
	  rs1.addProject(new ResearchProject("Shiza soseda",10));
	  rs1.pendProject("Shiza soseda");
	  
	  Student s2 = new Student("r","rrr",0);
	  Student s3 = new Student("k","kkk",0);
	  Student s4 = new Student("l","lll",0);
	  Student s5 = new Student("a","aaa",0);
	  DbContext.getInstance().addStudent(s1);
	  DbContext.getInstance().addStudent(s2);
	  DbContext.getInstance().addStudent(s3);
	  DbContext.getInstance().addStudent(s4);
	  DbContext.getInstance().addStudent(s5);
	   
	  Course c1 = new Course("ADS",5,"IS",2);
	  Course c2 = new Course("Physics 2",3,"Techsomething",2);
	  Course c3 = new Course("English C1",3,"General",1);
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