package model;

import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

public class TechSupportSpecialist extends Employee {

    private String report;
    private List<Request> pendingRequests;

    public TechSupportSpecialist(String name, String surname, double salary, Date hireDate, String login, String password) {
        super(name, surname, salary, hireDate, login, password);
        this.pendingRequests = new ArrayList<Request>();
        this.report = "";
    }
    
    public TechSupportSpecialist(String login, String password) {
    	super(login,password);
    }

    public void updateReport(String newInfo) {
        this.report += "\n" + newInfo;
    }

    public void changeRequestStatus(Request request, RequestStatus newStatus) {
        if (pendingRequests.contains(request)) {
            request.setStatus(newStatus);
        }
    }

    public List<Request> getPendingRequests() {
        return pendingRequests; // Here later take pending request from DbContext
    }

    public String getReport() {
        return report;
    }

    public void addRequest(Request r) {
        this.pendingRequests.add(r);
    }
}