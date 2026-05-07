package model;

import java.io.Serializable;

public class Comment implements Serializable{

    private User author;
    private String content;

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
    
    public void setContent(String content) {
    	this.content = content;
    }
}