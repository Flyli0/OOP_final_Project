import java.sql.Date;

abstract class User {

    private String name;
    private String surname;
    private String login;
    private Date birthDate;
    private Gender gender;
    private Language language;


    private static int idCounter = 0;
    private int id;
    private String password;

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.id = idCounter++;
    }

    public String getName() {
        return name + ' ' + surname;
    }
    public int getId() {
        return id;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public Date getBirthDate() {
        return birthDate;
    }

    public void changeLanguage(Language language){
        this.language = language;
    }

    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || obj instanceof User == false) return false;

        return this.id == ((User) obj).id;
    };

    public abstract String toString();


    
}
