package model;

import java.util.Date;

public class Request {

    private RequestStatus status; 
    private String info;
    private Date receivingDate;

    public Request(String info) {
        this.info = info;
        this.receivingDate = new Date(); 
        this.status = RequestStatus.VIEWED;
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