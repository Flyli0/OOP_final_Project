package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public abstract class User implements ImUser, Serializable { 

    private String name;
    private String surname;
    private String login;
    private String password;
    private Date birthDate;
    private Gender gender;
    private Language language;
    private List<String> journalNotifications;

    private static int idCounter = 0;
    private int id;
    private String system_id;

    public User(String login, String password, int a) {
        this.login = login;
        this.password = password;
        this.id = ++idCounter;
        int current_year = new Date().getYear();
        this.system_id = current_year + "B0" + this.id;
    }

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.id = ++idCounter;
        int current_year = new Date().getYear();
        this.system_id = current_year + "B0" + this.id;
    }

    public User(String name, String surname, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.id = ++idCounter;
        this.login = login;
        this.password = password;
        int current_year = new Date().getYear();
        this.system_id = current_year + "B0" + this.id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name + " " + surname;
    }

    public String getFirstName() { return name; }
    public String getLastName() { return surname; }

    // --- Добавленные методы для изменения имени и фамилии ---
    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    // --------------------------------------------------------

    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void changeLanguage(Language language) {
        this.language = language;
    }

    public String getSystemId() {
        return this.system_id;
    }

    public void update(String message) {
        this.journalNotifications.add(message);
    }
    
    public Date getBirthday() {
    	return this.birthDate;
    }
    
    public void setBirthday(Date date) {
    	this.birthDate = date;
    }

    public Gender getGender() {
    	return this.gender;
    }
    
    public void setGender(Gender g) {
    	this.gender = g;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User other = (User) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

}