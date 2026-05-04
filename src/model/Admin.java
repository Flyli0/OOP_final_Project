package model;

import java.sql.Date;

public class Admin extends Employee {

    public Admin(String name, String surname, double salary, Date hireDate, String login, String password) {
        super(name, surname, salary, hireDate, login, password);
    }
    
    public Admin(String login, String password) {
    	super(login,password);
    }

    public void manageUser(User u) {
        // TODO: здесь позже напишем логику управления юзерами
    }
}