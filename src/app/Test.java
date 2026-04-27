package app;

import model.Employee;

public class Test {
    public static void main(String[] args) {
        Employee emp1 = new Employee("John", "Doe", 50000, new java.sql.Date(System.currentTimeMillis()));
        emp1.setLogin("johndoe");

        System.out.println(emp1.toString());
    }
}