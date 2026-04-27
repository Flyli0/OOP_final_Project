package model;

import java.util.List;
import java.util.ArrayList;

public class News {

    private int id;
    private String header;
    private String topic; // Добавлено специально под требования ТЗ
    private String content;
    private User author;
    private boolean isPinned;
    private List<Comment> comments;

    public News(int id, String header, String topic, String content, User author) {
        this.id = id;
        this.header = header;
        this.topic = topic;
        this.content = content;
        this.author = author;
        // По ТЗ: если топик "Research", новость автоматически закрепляется
        this.isPinned = topic.equalsIgnoreCase("Research");
        this.comments = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public String getTopic() {
        return topic;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
}