package model;

import java.sql.Date;
import java.io.BufferedReader;

import service.UsersManageService;

public class Admin extends Employee {
    private transient UsersManageService ums = new UsersManageService();

    public Admin(String name, String surname, double salary, Date hireDate, String login, String password) {
        super(name, surname, salary, hireDate, login, password);
    }
    
    public Admin(String login, String password) {
    	super(login,password);
    }

    public void updateUser(User u) {
        ums.updateUser(u);
    }

    public void addUser(User u) {
        ums.addUser(u);
    }
}