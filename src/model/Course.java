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

    public void addTeacher() {
        // Убрали ошибочный return null;
    }

    public void viewTeachers() {
        // Убрали ошибочный return null;
    }

    public List<Teacher> getTeachers() {
        return teachers;
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