package model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Course implements Serializable {

    private String name;
    private String id;
    private School school;
    private int credits;
    private List<Teacher> teachers;
    private CourseType type;
    private List<Lesson> lessons;

    private String targetMajor;
    private int yearOfStudy;

    public Course() {
        this.teachers = new ArrayList<>();
        this.lessons = new ArrayList<>();
    }

    public Course(String name, int credits, String targetMajor, int yearOfStudy) {
        this.name = name;
        this.credits = credits;
        this.targetMajor = targetMajor;
        this.yearOfStudy = yearOfStudy;
        this.teachers = new ArrayList<>();
        this.lessons = new ArrayList<>();
    }

    public String getCourseName() { return this.name; }
    public int getCredits() { return this.credits; }
    public String getTargetMajor() { return this.targetMajor; }
    public int getYearOfStudy() { return this.yearOfStudy; }
    public CourseType getType() { return this.type; }

    public void addTeacher(Teacher t) {
        if (!teachers.contains(t)) {
            this.teachers.add(t);
        }
    }

    public List<Teacher> getTeachers() {
        return this.teachers;
    }

    public void viewTeachers() {
        if (teachers.isEmpty()) {
            System.out.println("No teachers assigned yet.");
        } else {
            for(Teacher t : teachers) {
                System.out.println(t.getFirstName() + " " + t.getLastName());
            }
        }
    }

    public enum CourseType {
        FREE, OFFTRACK, REQUIRED, GENERAL, MINOR, CORE
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course c = (Course) o;
        return name.equals(c.getCourseName());
    }

    @Override
    public String toString() {
        return name + " (" + credits + " credits) | Target: " + targetMajor + " | Year: " + yearOfStudy;
    }
}