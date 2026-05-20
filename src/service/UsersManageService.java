package service;

import config.DbContext;
import model.ResearcherDecorator;
import model.User;
import java.util.Scanner;

public class UsersManageService {

    DbContext db = DbContext.getInstance();

    public UsersManageService() {
    }


    public void updateUser(User u) {
        if (!db.allUsers().contains(u)) {
            System.out.println("❌ User not found!");
            return;

        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter new first name:");
        String name = sc.nextLine();

        System.out.println("Enter new last name:");
        String surname = sc.nextLine();
        
        System.out.println("Do you want to make this user a Researcher? y/n");
        String ans = sc.nextLine();
        if(ans.toLowerCase().equals("y") || ans.toLowerCase().equals("yes")) {
        	u = new ResearcherDecorator(u);
        }

        u.setName(name);
        u.setSurname(surname);
        sc.close();


        DbContext.saveUsers();
        System.out.println("✅ User data successfully updated!");
    }

    public void addUser(User u) {
        if (db.allUsers().contains(u)) {
            System.out.println("❌ This user already exists in the system!");
            return;
        }
        db.allUsers().add(u);
        DbContext.saveUsers();
    }

    public void deleteUser(User u) {
        if (!db.allUsers().contains(u)) {
            System.out.println("❌ User not found!");
            return;
        }
        db.allUsers().remove(u);
        DbContext.saveUsers();
    }
}