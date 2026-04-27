package model;

import java.util.Map;
import java.util.HashMap;

public class Journal {

    private Map<Course, Mark> journal;

    public Journal() {
        // Инициализируем пустой журнал, чтобы избежать NullPointerException
        this.journal = new HashMap<>();
    }

    public void update(Course course, Mark mark) {
        // Добавляем или обновляем оценку за конкретный курс
        this.journal.put(course, mark);
    }

    public Map<Course, Mark> getJournal() {
        return journal;
    }
}