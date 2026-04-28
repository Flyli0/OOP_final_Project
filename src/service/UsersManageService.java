package service;

import java.io.*;
import java.util.*;

import config.DbContext;
import model.User;

/**
 * 
 */
public class UsersManageService {

    /**
     * Default constructor
     */
    DbContext db = DbContext.getInstance();
    public UsersManageService() {
    }


    /**
     * @param User u 
     * @return
     */
    public void updateUser( User u) {
        // TODO implement here
        if (!db.allUsers().contains(u)) {
            System.out.println("User not found!");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter new name:");  
        String name = sc.nextLine();

        System.out.println("Enter new surname:");
        String surname = sc.nextLine();

        System.out.println("Enter new login:");
        String login = sc.nextLine();

        u.setName(name);
        u.setSurname(surname);
        u.setLogin(login);
        sc.close();

        DbContext.save();
    }

    /**
     * @param User u 
     * @return
     */ 
    public void deleteUser( User u) {
        // TODO implement here
        if (!db.allUsers().contains(u)) {
            System.out.println("User not found!");
            return;
        }
        
        db.allUsers().remove(u);

        DbContext.save();
    }

    /**
     * @param User u 
     * @return
     */
    public void addUser( User u ) {
        // TODO implement here
        db.allUsers().add(u);
        DbContext.save();
    }

}