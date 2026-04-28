package model;

import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

public class Teacher extends Employee {

    private TeacherTitle title; 
    private List<ScheduleEntry> schedule;

    public Teacher(String name, String surname, double salary, Date hireDate, TeacherTitle title) {
        super(name, surname, salary, hireDate);
        this.title = title;
        this.schedule = new ArrayList<>();
    }

    public void putMark(Student s, Course c, Mark m) {
        // TODO: логика выставления оценки (Part C)
        System.out.println("Mark " + m.getValue() + " put for student " + s.getName());
    }

    public void sendComplaint(Student s, Complaint.UrgencyLevel ul) {
        // TODO: логика отправки жалобы
    }

    public void manageCourse(Course c) {
        // TODO: логика управления курсом
    }

    public void viewStudents() {
        // TODO: логика просмотра списка студентов
    }

    public void viewCourses() {
        // TODO: логика просмотра курсов
    }

    public TeacherTitle getTitle() {
        return title;
    }

    public List<ScheduleEntry> getSchedule() {
        return schedule;
    }
}