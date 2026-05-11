package model;

import java.util.List;
import java.util.ArrayList;

public class Complaint {

    private List<Student> students;
    private UrgencyLevel urgency;
    private String text;
    private Teacher sender;

    public Complaint() {
        this.students = new ArrayList<>(); 
    }

    public void writeComplaint(String s) {
        this.text = s;
    }

    public void addStudent(Student s) {
        this.students.add(s);
    }

    public enum UrgencyLevel {
        LOW,
        MEDIUM,
        HIGH
    }
}