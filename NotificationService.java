package service;

import engine.NotificationEngine;
import model.Bug;
import model.Notification;
import java.util.List;

public class NotificationService {
    private final NotificationEngine engine;

    public NotificationService(NotificationEngine engine) {
        this.engine = engine;
    }

    public void notifyBugAssignment(Bug bug) {
        if (bug.getAssignee() != null && !bug.getAssignee().isBlank()) {
            engine.sendNotification(bug.getAssignee(),
                    "You have been assigned bug #" + bug.getId() + " - " + bug.getTitle());
        }
    }

    public void notifyDeadlineApproaching(Bug bug, int daysLeft) {
        if (bug.getAssignee() != null && !bug.getAssignee().isBlank()) {
            engine.sendNotification(bug.getAssignee(),
                    "Deadline approaching in " + daysLeft + " days for bug #" + bug.getId());
        }
    }

    public void notifyStatusChange(Bug bug) {
        engine.sendNotification(bug.getReporter(),
                "Bug #" + bug.getId() + " status changed to " + bug.getStatus());
    }

    public List<Notification> getAllNotifications() {
        return engine.getAllNotifications();
    }
}