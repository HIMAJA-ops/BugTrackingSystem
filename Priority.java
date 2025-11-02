package model;

public enum Priority {
    LOW, MEDIUM, HIGH;

    public static Priority fromStringOrDefault(String s, Priority def) {
        if (s == null || s.isBlank()) return def;
        try { return Priority.valueOf(s.trim().toUpperCase()); }
        catch (Exception e) { return def; }
    }
}