import java.sql.Date;

class Employee extends User {
    private double salary;
    private Date hireDate;
    
    public Employee(String name, String surname, double salary, Date hireDate) {
        super(name, surname);
        this.salary = salary;
        this.hireDate = hireDate;
    }

    

    public double getSalary() {
        return salary;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void SendRequest(){

    }


    @Override
    public String toString() {
        return "Employee{" +
                "name=" + getName() + 
                ", id=" + getId() +
                ", login=" + getLogin() +
                ", salary=" + salary +
                ", hireDate=" + hireDate +
                '}';
    }

    

}