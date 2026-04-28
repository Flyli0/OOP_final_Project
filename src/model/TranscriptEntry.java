package model;

public class TranscriptEntry {

    private Semester semester;
    private Course course;
    private Mark mark;


    public TranscriptEntry() {
    }

    public TranscriptEntry(Course course, Mark mark, Semester semester) {
        this.course = course;
        this.mark = mark;
        this.semester = semester;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        String courseName = (course != null) ? course.getCourseName() : "Unknown Course";
        double markValue = (mark != null) ? mark.getValue() : 0.0;
        return courseName + ": " + markValue + " (" + semester + ")";
    }
}