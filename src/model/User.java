package model;

import java.io.Serializable;
import java.util.Date;

public class User implements ImUser, Serializable { 

    private String name;
    private String surname;
    private String login;
    private String password;
    private Date birthDate;
    private Gender gender;
    private Language language;

    private static int idCounter = 0;
    private int id;
    
    public User(String login, String password, int a) {
    	this.login = login;
    	this.password = password;
    }
    
    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.id = ++idCounter;
    }
    
    public User(String name, String surname, String login, String password) {
    	this.name = name;
        this.surname = surname;
        this.id = ++idCounter;
        this.login = login;
        this.password = password;
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