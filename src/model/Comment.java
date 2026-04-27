package model;

public class Comment {

    private User author;
    private String content;

    // Добавил нормальный конструктор, чтобы можно было создавать комментарии
    public Comment(User author, String content) {
        this.author = author;
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}