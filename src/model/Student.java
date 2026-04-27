package model;

import java.util.List;
import java.util.ArrayList;

public class Student extends User {

    private int creditsGot;
    private Transcript transcript;
    private List<Course> courses;
    private List<ScheduleEntry> schedule;
    // private EnrollmentService es; // Пока закомментируем, если файла EnrollmentService еще нет

    public Student(String name, String surname) {
        super(name, surname);
        this.courses = new ArrayList<>();
        this.schedule = new ArrayList<>();
        this.creditsGot = 0;
        this.transcript = new Transcript(); // Создаем пустой транскрипт при регистрации
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void viewCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses registered.");
        } else {
            courses.forEach(System.out::println);
        }
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public int getCreditsNum() {
        return creditsGot;
    }

    public void addCredits(int amount) {
        this.creditsGot += amount;
    }

    public void createSchedule() {
        // TODO: логика создания расписания
    }
}