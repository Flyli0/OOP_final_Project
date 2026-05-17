package model;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class Student extends User implements Serializable{

    private int creditsGot;
    private Transcript transcript;
    private List<Course> courses;
    private List<ScheduleEntry> schedule;

    public Student(String name, String surname) {
        super(name, surname);
        this.courses = new ArrayList<>();
        this.schedule = new ArrayList<>();
        this.creditsGot = 0;
        this.transcript = new Transcript();
    }
    
    public Student(String login, String password, int z) {
    	super(login, password);
        this.courses = new ArrayList<>();
        this.schedule = new ArrayList<>();
        this.creditsGot = 0;
        this.transcript = new Transcript();
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

    public List<ScheduleEntry> getSchedule() {
        return schedule;
    }

    public void addCredits(int amount) {
        this.creditsGot += amount;
    }

    public void createSchedule() {
        // TODO: логика создания расписания
    }

	@Override
	public String toString() {
		return "Student: " + super.getFirstName() + ' ' + super.getLastName() + " " + super.getSystemId();
	}
}