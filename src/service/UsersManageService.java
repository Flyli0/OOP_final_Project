package service;

import config.DbContext;
import model.User;
import java.util.Scanner;

public class UsersManageService {

    DbContext db = DbContext.getInstance();

    public UsersManageService() {
    }


    public void updateUser(User u) {
        if (!db.allUsers().contains(u)) {
            System.out.println("❌ Пользователь не найден!"); 
            return; 
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Введите новое имя:");
        String name = sc.nextLine();

        System.out.println("Введите новую фамилию:");
        String surname = sc.nextLine();


        u.setName(name);
        u.setSurname(surname);

        DbContext.saveUsers(); 
        System.out.println("✅ Данные пользователя успешно обновлены!");
    }

    public void addUser(User u) {
        if (db.allUsers().contains(u)) {
            System.out.println("❌ Такой пользователь уже существует в системе!");
            return;
        }
        db.addUser(u); 
        System.out.println("✅ Пользователь успешно зарегистрирован!");
    }

    public void removeUser(User u) {
        if (db.allUsers().contains(u)) {
            db.allUsers().remove(u);
            DbContext.saveUsers();   
            System.out.println("✅ Пользователь успешно удален из системы!");
        } else {
            System.out.println("❌ Ошибка: Пользователь не найден!");
        }
    }
}