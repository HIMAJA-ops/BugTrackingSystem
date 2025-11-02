package ui;

import model.*;
import service.BugService;
import java.util.*;

public class ConsoleUI {
    private final BugService service;
    private final Scanner sc = new Scanner(System.in);

    public ConsoleUI(BugService service) {
        this.service = service;
    }

    public void run() {
        System.out.println("=== Simple Bug Tracker (Core Java) ===");
        boolean loop = true;
        while (loop) {
            printMenu();
            String choice = prompt("Choice");
            switch (choice) {
                case "1": createBug(); break;
                case "2": listBugs(); break;
                case "3": viewBug(); break;
                case "4": updateBug(); break;
                case "5": changeStatus(); break;
                case "6": addComment(); break;
                case "7": deleteBug(); break;
                case "8": searchBugs(); break;
                case "9": loop = false; break;
                default: System.out.println("Unknown option.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n1) Create bug");
        System.out.println("2) List bugs");
        System.out.println("3) View bug");
        System.out.println("4) Update bug");
        System.out.println("5) Change status / assign / priority");
        System.out.println("6) Add comment");
        System.out.println("7) Delete bug");
        System.out.println("8) Search");
        System.out.println("9) Exit");
    }

    private String prompt(String label) {
        System.out.print(label + ": ");
        return sc.nextLine().trim();
    }

    private void createBug() {
        String title = prompt("Title");
        String desc = prompt("Description");
        String reporter = prompt("Reporter");
        String assignee = prompt("Assignee (optional)");
        String p = prompt("Priority (LOW, MEDIUM, HIGH) [MEDIUM]");
        Priority pr = Priority.fromStringOrDefault(p, Priority.MEDIUM);
        Bug b = service.createBug(title, desc, reporter, assignee.isBlank() ? null : assignee, pr);
        System.out.println("Created bug id=" + b.getId());
    }

    private void listBugs() {
        List<Bug> all = service.listAll();
        if (all.isEmpty()) { System.out.println("No bugs."); return; }
        System.out.printf("%-5s %-20s %-10s %-10s %-10s%n", "ID", "Title", "Status", "Priority", "Assignee");
        for (Bug b : all) {
            System.out.printf("%-5d %-20s %-10s %-10s %-10s%n",
                    b.getId(), shorten(b.getTitle(), 20), b.getStatus(), b.getPriority(),
                    b.getAssignee() == null ? "-" : b.getAssignee());
        }
    }

    private void viewBug() {
        int id = readId();
        Bug b = service.get(id);
        if (b == null) { System.out.println("Not found."); return; }
        System.out.println(b.detailedString());
    }

    private void updateBug() {
        int id = readId();
        Bug b = service.get(id);
        if (b == null) { System.out.println("Not found."); return; }
        String title = prompt("New title (leave blank to keep)");
        String desc = prompt("New description (leave blank to keep)");
        if (!title.isBlank()) b.setTitle(title);
        if (!desc.isBlank()) b.setDescription(desc);
        service.updateBug(b);
        System.out.println("Updated.");
    }

    private void changeStatus() {
        int id = readId();
        Bug b = service.get(id);
        if (b == null) { System.out.println("Not found."); return; }
        String status = prompt("Status (OPEN, IN_PROGRESS, RESOLVED, CLOSED) [" + b.getStatus() + "]");
        if (!status.isBlank()) b.setStatus(Status.valueOf(status.toUpperCase()));
        String assignee = prompt("Assignee (leave blank to keep/remove)");
        if (!assignee.isBlank()) b.setAssignee(assignee);
        String p = prompt("Priority (LOW, MEDIUM, HIGH) [" + b.getPriority() + "]");
        if (!p.isBlank()) b.setPriority(Priority.fromStringOrDefault(p, b.getPriority()));
        service.updateBug(b);
        System.out.println("Updated status/assignment/priority.");
    }

    private void addComment() {
        int id = readId();
        Bug b = service.get(id);
        if (b == null) { System.out.println("Not found."); return; }
        String author = prompt("Your name");
        String text = prompt("Comment text");
        service.addComment(id, author, text);
        System.out.println("Comment added.");
    }

    private void deleteBug() {
        int id = readId();
        boolean ok = service.delete(id);
        System.out.println(ok ? "Deleted." : "Not found.");
    }

    private void searchBugs() {
        String q = prompt("Text to search in title/description");
        List<Bug> res = service.search(q);
        if (res.isEmpty()) { System.out.println("No matches."); return; }
        for (Bug b : res) System.out.println(b.briefString());
    }

    private int readId() {
        try { return Integer.parseInt(prompt("Bug id")); }
        catch (Exception e) { return -1; }
    }

    private String shorten(String s, int len) {
        return s.length() <= len ? s : s.substring(0, len - 3) + "...";
    }
}