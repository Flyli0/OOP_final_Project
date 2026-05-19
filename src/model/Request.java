package model;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable{

    private RequestStatus status; 
    private String info;
    private static int idCounter = 0;
    private int id;
    private Date receivingDate;
    

    public Request(String info) {
        this.info = info;
        this.receivingDate = new Date(); 
        this.status = RequestStatus.VIEWED;
        this.id = ++idCounter;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public Date getReceivingDate() {
        return receivingDate;
    }

    public String getInfo() {
        return info;
    }
    
    public int getId() {
    	return this.id;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Request{" +
                "status=" + status +
                ", info='" + info + '\'' +
                ", receivingDate=" + receivingDate +
                '}';
    }
}