package model;

import java.util.Map;
import java.util.HashMap;

public class Journal {

    private Map<Course, Mark> journal;

    public Journal() {
        this.journal = new HashMap<>();
    }

    public void update(Course course, Mark mark) {
        this.journal.put(course, mark);
    }

    public Map<Course, Mark> getJournal() {
        return journal;
    }
}