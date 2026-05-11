package model;

import java.io.IOException;
import java.sql.Date;

import config.DbContext;
import service.EnrollmentService;

public class Manager extends Employee {
    private ManagerType type;

    public Manager(String name, String surname, double salary, Date hireDate, ManagerType type, String login, String password) {
        super(name, surname, salary, hireDate, login, password);
        this.type = type;
    }
    
    public Manager(String login, String password) {
    	super(login,password);
    }

    public void approveRegistration(Enrollment en, boolean choice) throws NumberFormatException, IOException {
    	EnrollmentService.approveStudents(en);
    }
    


    public ManagerType getManagerType() {
        return type;
    }
    
}