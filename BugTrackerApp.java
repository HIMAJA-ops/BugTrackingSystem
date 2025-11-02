package app;

import repository.BugRepository;
import service.*;
import engine.NotificationEngine;
import ui.ConsoleUI;

public class BugTrackerApp {
    public static void main(String[] args) {
        BugRepository repo = BugRepository.loadFromFile("bugs.db");
        NotificationEngine engine = new NotificationEngine();
        NotificationService notifier = new NotificationService(engine);
        BugService service = new BugService(repo, notifier);
        ConsoleUI ui = new ConsoleUI(service);
        ui.run();
        repo.saveToFile("bugs.db");
        System.out.println("Exiting. Data saved to bugs.db");
    }
}