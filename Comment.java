package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String author;
    private final String text;
    private final LocalDateTime when = LocalDateTime.now();

    public Comment(String author, String text) {
        this.author = author;
        this.text = text;
    }

    @Override
    public String toString() {
        return author + " (" + when.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "): " + text;
    }
}