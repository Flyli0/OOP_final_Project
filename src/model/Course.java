package model;

import java.io.Serializable;
import java.util.List;

public class Course implements Serializable{

    private String name;
    private String id;
    private School school; 
    private int credits;
    private List<Teacher> teachers;
    private CourseType type;
    private List<Lesson> lessons;

    public Course() {
    }
    
    public Course(String name, int credits) {
    	this.name= name;
    	this.credits = credits;
    }
   
    
    public String getCourseName() {
    	return this.name;
    }

    public void addTeacher(Teacher t) {
        this.teachers.add(t);
    }

    public void viewTeachers() {
        for(Teacher t :teachers) {
        	System.out.println(t);
        }
    }

    public List<Teacher> getTeachers() {
        return this.teachers;
    }

    public enum CourseType {
        FREE,
        OFFTRACK,
        REQUIRED,
        GENERAL,
        MINOR,
        CORE
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;

        Course c = (Course) o;

        return name.equals(c.getCourseName());
    }
}