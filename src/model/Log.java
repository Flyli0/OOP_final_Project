package model;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable{
    private Date date;
    private String action;
    private String userId;
    public Log(String action, String userId) {
        this.date = new Date();
        this.action = action;
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public String getAction() {
        return action;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Log{" +
                "date=" + date +
                ", action='" + action + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
