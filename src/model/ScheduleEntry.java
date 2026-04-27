package model;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class ScheduleEntry {

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
        // Берем курс напрямую из урока, чтобы данные не дублировались
        return (lesson != null) ? lesson.getCourse() : null;
    }

    public List<Date> getDays() {
        return days;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public int getRoomLoad() {
        // Логика расчета загруженности комнаты (если нужно для Part C)
        return 0;
    }
}