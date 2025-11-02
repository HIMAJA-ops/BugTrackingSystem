package engine;

import model.Notification;
import java.util.*;

public class NotificationEngine {
    private final List<Notification> sentNotifications = new ArrayList<>();

    public void sendNotification(String recipient, String message) {
        Notification n = new Notification(recipient, message);
        sentNotifications.add(n);
        // For console-based version, we just print the notification.
        System.out.println("\n🔔 Notification: " + n);
    }

    public List<Notification> getAllNotifications() {
        return Collections.unmodifiableList(sentNotifications);
    }
}