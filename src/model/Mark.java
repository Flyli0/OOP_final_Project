package model;

import java.io.Serializable;
import java.util.Date;

public class Mark implements Serializable{

    private double value;
    private Date date;

    public Mark(double value, Date date) {
        this.value = value;
        this.date = date;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "value=" + value +
                ", date=" + date +
                '}';
    }
}