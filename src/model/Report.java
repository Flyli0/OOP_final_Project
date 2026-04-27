package model;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;

public class Report {

    private int id;
    private Date date;
    private String content;
    private Map<Student, Mark> marks;

    public Report(int id, String content) {
        this.id = id;
        this.content = content;
        this.date = new Date(); // Ставим текущую дату создания отчета
        this.marks = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<Student, Mark> getMarks() {
        return marks;
    }

    public void addMarkToReport(Student student, Mark mark) {
        this.marks.put(student, mark);
    }
}