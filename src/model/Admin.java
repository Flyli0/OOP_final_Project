package model;

import java.sql.Date;

public class Admin extends Employee {

    public Admin(String name, String surname, double salary, Date hireDate) {
        super(name, surname, salary, hireDate);
    }

    public void manageUser(User u) {
        // TODO: здесь позже напишем логику управления юзерами
    }
}