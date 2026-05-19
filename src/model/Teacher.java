package model;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.Date;

import config.DbContext;
import model.Complaint;

public class Teacher extends Employee implements Serializable{

    private TeacherTitle title; 
    private List<ScheduleEntry> schedule;

    public Teacher(String name, String surname, double salary, Date hireDate, TeacherTitle title, String login, String password) {
        super(name, surname, salary, hireDate, login, password);
        this.title = title;
        this.schedule = new ArrayList<>();
    }
    
    public Teacher(String login, String password) {
    	super(login, password);
    }
    
    public void setTitle(TeacherTitle title) {
    	this.title = title;
    }

    public void putMark(Student s, Course c, Mark m) {
        System.out.println("Mark " + m.getValue() + " put for student " + s.getName());
    }

    public void sendComplaint(Student student, String message, UrgencyLevel urgency) {
        Complaint complaint = new Complaint(this, student, message, urgency);

        DbContext.getInstance().addComplaint(complaint);

        System.out.println("Complaint successfully sent to manager/dean!");
    }


    public TeacherTitle getTitle() {
        return title;
    }

    public List<ScheduleEntry> getSchedule() {
        if (schedule == null) schedule = new ArrayList<>();
        return schedule;
    }
}