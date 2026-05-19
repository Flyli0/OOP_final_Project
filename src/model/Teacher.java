package model;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.Date;
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

    public void sendComplaint(Student s, Complaint.UrgencyLevel ul) {
        // TODO: логика отправки жалобы
    }

    public TeacherTitle getTitle() {
        return title;
    }

    public List<ScheduleEntry> getSchedule() {
        if (schedule == null) schedule = new ArrayList<>();
        return schedule;
    }
}