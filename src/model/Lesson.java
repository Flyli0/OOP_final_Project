package model;

import java.util.Date;

public class Lesson {

    private String title;
    private String room;
    private LessonType type; 
    private Teacher instructor;
    private Course course;
    private Date time; 

    public Lesson() {
    }

    public Lesson(String title, String room, LessonType type, Teacher instructor, Course course, Date time) {
        this.title = title;
        this.room = room;
        this.type = type;
        this.instructor = instructor;
        this.course = course;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getRoom() {
        return room;
    }

    public LessonType getType() {
        return type;
    }

    public Teacher getInstructor() {
        return instructor;
    }

    public Course getCourse() {
        return course;
    }

    public Date getTime() {
        return time;
    }
}