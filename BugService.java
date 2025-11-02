package service;

import model.*;
import repository.BugRepository;
import java.util.*;

public class BugService {
    private final BugRepository repo;
    private final NotificationService notifier;

    public BugService(BugRepository repo, NotificationService notifier) {
        this.repo = repo;
        this.notifier = notifier;
    }

    public Bug createBug(String title, String desc, String reporter, String assignee, Priority priority) {
        Bug b = new Bug(title, desc, reporter, assignee, priority);
        repo.save(b);
        notifier.notifyBugAssignment(b);
        return b;
    }

    public Bug get(int id) { return repo.findById(id); }

    public List<Bug> listAll() { return repo.findAll(); }

    public void updateBug(Bug b) {
        repo.save(b);
        notifier.notifyStatusChange(b);
    }

    public boolean delete(int id) { return repo.delete(id); }

    public void addComment(int id, String author, String text) {
        Bug b = repo.findById(id);
        if (b == null) throw new NoSuchElementException("Bug not found");
        b.getComments().add(new Comment(author, text));
        repo.save(b);
    }

    public List<Bug> search(String query) {
        return repo.findAll().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(query.toLowerCase())
                        || b.getDescription().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }
}