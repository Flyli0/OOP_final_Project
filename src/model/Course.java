package model;

import java.util.List;

public class Course {

    private String name;
    private String id;
    private School school; // Раскомментировали, так как School теперь существует
    private int credits;
    private List<Teacher> teachers;
    private CourseType type;
    private List<Lesson> lessons;

    public Course() {
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
}