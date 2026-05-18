package model;

import java.util.Date;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class ScheduleEntry implements Serializable{

    private Lesson lesson;
    private List<Date> days;

    public ScheduleEntry(Lesson lesson) {
        this.lesson = lesson;
        this.days = new ArrayList<>();
    }

    public void addDay(Date dt) {
        this.days.add(dt);
    }

    public Course getCourse() {
        return (lesson != null) ? lesson.getCourse() : null;
    }

    public List<Date> getDays() {
        return days;
    }

    public Lesson getLesson() {
        return lesson;
    }
}