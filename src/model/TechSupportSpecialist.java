package model;

import java.util.List;

import config.DbContext;

import java.util.ArrayList;
import java.sql.Date;

public class TechSupportSpecialist extends Employee{

    private String report;
    private List<Request> requests = DbContext.getInstance().allRequests();
    private List<Request> accepted;

    public TechSupportSpecialist(String name, String surname, double salary, Date hireDate, String login, String password) {
        super(name, surname, salary, hireDate, login, password);
        this.report = "";
    }
    
    public TechSupportSpecialist(String login, String password) {
    	super(login,password);
    	accepted = new ArrayList<Request>();
    }

    public void updateReport(String newInfo) {
        this.report += "\n" + newInfo;
    }

    public void changeRequestStatus(Request request, RequestStatus newStatus) {
        if (requests.contains(request)) {
            request.setStatus(newStatus);
        }
    }

    public List<Request> getPendingRequests() {
        List<Request> pending = DbContext.getInstance().allRequests().stream().filter(request -> request.getStatus().equals(RequestStatus.VIEWED)).toList();
        return pending;
    }

    public String getReport() {
        return report;
    }
    
    public List<Request> getAccepted(){
    	return this.accepted;
    }
    
    public boolean acceptRequest(int id){
    	Request r = DbContext.getInstance().allRequests().stream().filter(req -> req.getId()==id).findFirst().orElse(null);
    	if(r == null) { System.out.println("Wrong format"); return false;}
    	this.accepted.add(r);
    	r.setStatus(RequestStatus.ACCEPTED);
    	DbContext.saveRequests();
    	return true;
    }
    
    public boolean doneRequest(int id) {
    	Request r = DbContext.getInstance().allRequests().stream().filter(req -> req.getId()==id).findFirst().orElse(null);
    	if(r == null) { System.out.println("Wrong format"); return false;}
    	this.accepted.remove(r);
    	r.setStatus(RequestStatus.DONE);
    	DbContext.saveRequests();
    	return true;
    }
    
    public boolean rejectRequest(int id) {
    	Request r = DbContext.getInstance().allRequests().stream().filter(req -> req.getId()==id).findFirst().orElse(null);
    	if(r == null) { System.out.println("Wrong format"); return true;}
    	r.setStatus(RequestStatus.REJECTED);
    	DbContext.saveRequests();
    	return false;
    }

    public void addRequest(Request r) {
        DbContext.getInstance().addRequest(r);
    }
}