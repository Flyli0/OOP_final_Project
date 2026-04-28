package model;

import java.sql.Date;

public class Manager extends Employee {

    private ManagerType type;
    // private NewsService ns; // Пока оставим закомментированным, чтобы не было ошибки из-за сервисов

    public Manager(String name, String surname, double salary, Date hireDate, ManagerType type) {
        super(name, surname, salary, hireDate);
        this.type = type;
    }

    public void assignCourse(Teacher t, Course c) {

    }

    public void approveRegistration(Student s) {
        // TODO: логика одобрения регистрации
    }

    public ManagerType getManagerType() {
        return type;
    }

    public void manageNews() {
        // TODO: логика управления новостями
    }
}