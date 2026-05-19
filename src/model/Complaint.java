package model;

import java.io.Serializable;

public class Complaint implements Serializable {
    private Teacher teacher;
    private Student student;
    private String message;
    private UrgencyLevel urgency;

    public Complaint(Teacher teacher, Student student, String message, UrgencyLevel urgency) {
        this.teacher = teacher;
        this.student = student;
        this.message = message;
        this.urgency = urgency;
    }

    public Teacher getTeacher() { return teacher; }
    public Student getStudent() { return student; }
    public String getMessage() { return message; }
    public UrgencyLevel getUrgency() { return urgency; }

    @Override
    public String toString() {
        return "Complaint from " + teacher.getClass().getSimpleName() +
                " to student " + student.getClass().getSimpleName() +
                " [" + urgency + "]: " + message;
    }
}