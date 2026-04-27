package model;

public class Message {

    private String id;
    private User address;
    private String content;

    public Message(String id, User address, String content) {
        this.id = id;
        this.address = address;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public User getAddress() {
        return address;
    }

    public String getContent() {
        return content;
    }
}