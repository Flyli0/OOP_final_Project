package service;

import config.DbContext;
import model.User;
import java.util.Scanner;

public class UsersManageService {

    // Подключаемся к нашей единой базе данных
    DbContext db = DbContext.getInstance();

    public UsersManageService() {
    }

    // 1. Метод для ОБНОВЛЕНИЯ пользователя [cite: 102]
    public void updateUser(User u) {
        if (!db.allUsers().contains(u)) {
            System.out.println("❌ Пользователь не найден!"); // Исправили Course.out на System.out
            return; // Прерываем работу, если юзера нет
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Введите новое имя:");
        String name = sc.nextLine();

        System.out.println("Введите новую фамилию:");
        String surname = sc.nextLine();

        // Физически обновляем данные в объекте
        u.setName(name);
        u.setSurname(surname);

        DbContext.saveUsers(); // Исправили ошибку сохранения
        System.out.println("✅ Данные пользователя успешно обновлены!");
    }

    // 2. Метод для ДОБАВЛЕНИЯ пользователя [cite: 102]
    public void addUser(User u) {
        if (db.allUsers().contains(u)) {
            System.out.println("❌ Такой пользователь уже существует в системе!");
            return;
        }
        db.addUser(u); // Этот метод внутри DbContext уже вызывает сохранение
        System.out.println("✅ Пользователь успешно зарегистрирован!");
    }

    // 3. Метод для УДАЛЕНИЯ пользователя [cite: 102]
    public void removeUser(User u) {
        if (db.allUsers().contains(u)) {
            db.allUsers().remove(u); // Удаляем из списка
            DbContext.saveUsers();   // Сохраняем изменения в файл
            System.out.println("✅ Пользователь успешно удален из системы!");
        } else {
            System.out.println("❌ Ошибка: Пользователь не найден!");
        }
    }
}