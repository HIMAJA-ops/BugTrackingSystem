package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Bug implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String description;
    private String reporter;
    private String assignee;
    private Status status = Status.OPEN;
    private Priority priority = Priority.MEDIUM;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final List<Comment> comments = new ArrayList<>();

    public Bug(String title, String description, String reporter, String assignee, Priority priority) {
        this.title = title;
        this.description = description;
        this.reporter = reporter;
        this.assignee = assignee;
        if (priority != null) this.priority = priority;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getReporter() { return reporter; }
    public String getAssignee() { return assignee; }
    public void setAssignee(String assignee) { this.assignee = assignee; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<Comment> getComments() { return comments; }

    public String briefString() {
        return String.format("ID:%d | %s | %s | %s", id, title, status, priority);
    }

    public String detailedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bug #").append(id).append(" - ").append(title).append("\n");
        sb.append("Status: ").append(status).append("  Priority: ").append(priority).append("\n");
        sb.append("Reporter: ").append(reporter).append("  Assignee: ").append(assignee).append("\n");
        sb.append("Created: ").append(createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append("\n\n");
        sb.append("Description:\n").append(description).append("\n\nComments:\n");
        if (comments.isEmpty()) sb.append("  (none)\n");
        else {
            for (Comment c : comments) sb.append(" - ").append(c).append("\n");
        }
        return sb.toString();
    }
}