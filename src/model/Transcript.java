package model;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class Transcript implements Serializable{

    public static class TranscriptEntry implements Serializable{
        public Course course;
        public Mark mark;
        public String semester; 

        public TranscriptEntry(Course course, Mark mark, String semester) {
            this.course = course;
            this.mark = mark;
            this.semester = semester;
        }

        @Override
        public String toString() {
            return course.getCourseName() + ": " + mark.getValue() + " (" + semester + ")";
        }
    }

    private List<TranscriptEntry> entries;

    public Transcript() {
        this.entries = new ArrayList<>();
    }

    public void addEntry(Course c, Mark m, String s) {
        this.entries.add(new TranscriptEntry(c, m, s));
    }

    public List<TranscriptEntry> getEntries() {
        return entries;
    }

    public double calculateTotalGPA() {
        if (entries.isEmpty()) return 0.0;
        double sum = 0;
        for (TranscriptEntry entry : entries) {
            sum += entry.mark.getValue();
        }
        return sum / entries.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("--- Transcript ---\n");
        for (TranscriptEntry entry : entries) {
            sb.append(entry.toString()).append("\n");
        }
        sb.append("Total GPA: ").append(calculateTotalGPA());
        return sb.toString();
    }
}