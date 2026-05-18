package service;

import config.DbContext;
import model.Message;
import model.User;

import java.util.Scanner;

public class MessageSendingService {

    DbContext db = DbContext.getInstance();
    Scanner sc = new Scanner(System.in);

    public MessageSendingService() {
    }

    public void sendMessage(User curUser) {
        System.out.println("Enter receiver login:");
        String receiver = sc.nextLine();
        System.out.println("Enter your message:");
        String text = sc.nextLine();

        Message msg = new Message(curUser.getLogin(), receiver, text);
        db.addMessage(msg);
        System.out.println("✅ Message sent!");
    }

    public void viewMyMessages() {
        System.out.println("Enter your login to check mail:");
        String myLogin = sc.nextLine();
        boolean hasMessages = false;

        for (Message m : db.allMessages()) {
            if (m.getReceiverLogin().equals(myLogin)) {
                System.out.println(m.toString());
                hasMessages = true;
            }
        }
        if (!hasMessages) System.out.println("📭 Inbox is empty.");
    }

    // 3. Реальное РЕДАКТИРОВАНИЕ
    public void redactMessagee(String content) {
        System.out.println("Enter ID of the message you want to edit:");
        int id = Integer.parseInt(sc.nextLine());

        Message msg = db.allMessages().stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);

        if (msg != null) {
            msg.setContent(content);
            DbContext.saveMessages();
            System.out.println("✅ Message updated!");
        } else {
            System.out.println("❌ Message with this ID not found.");
        }
    }

    // 4. Реальное УДАЛЕНИЕ
    public void deleteMessage(int id) {
        boolean removed = db.allMessages().removeIf(m -> m.getId() == id);

        if (removed) {
            DbContext.saveMessages();
            System.out.println("✅ Message deleted!");
        } else {
            System.out.println("❌ Message with this ID not found.");
        }
    }
}