package model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private static int idCounter = 0; // Счетчик для уникальных ID
    private int id;
    private String senderLogin;
    private String receiverLogin;
    private String content;
    private Date sendDate;

    public Message(String senderLogin, String receiverLogin, String content) {
        this.id = ++idCounter;
        this.senderLogin = senderLogin;
        this.receiverLogin = receiverLogin;
        this.content = content;
        this.sendDate = new Date();
    }

    public int getId() { return id; }
    public String getSenderLogin() { return senderLogin; }
    public String getReceiverLogin() { return receiverLogin; }
    public String getContent() { return content; }

    // Сеттер для редактирования текста
    public void setContent(String content) { this.content = content; }

    @Override
    public String toString() {
        return "ID: " + id + " | From: " + senderLogin + " | Date: " + sendDate + "\n" +
                "Message: " + content + "\n" +
                "-----------------------";
    }
}