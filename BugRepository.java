package repository;

import model.Bug;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BugRepository implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Map<Integer, Bug> store = new HashMap<>();
    private final AtomicInteger idGen = new AtomicInteger(1);

    public synchronized void save(Bug b) {
        if (b.getId() == 0) {
            int id = idGen.getAndIncrement();
            b.setId(id);
        } else {
            idGen.updateAndGet(curr -> Math.max(curr, b.getId() + 1));
        }
        store.put(b.getId(), b);
    }

    public synchronized Bug findById(int id) { return store.get(id); }

    public synchronized List<Bug> findAll() {
        ArrayList<Bug> list = new ArrayList<>(store.values());
        list.sort(Comparator.comparingInt(Bug::getId));
        return list;
    }

    public synchronized boolean delete(int id) { return store.remove(id) != null; }

    public synchronized void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        } catch (Exception e) {
            System.err.println("Failed to save repository: " + e.getMessage());
        }
    }

    public static BugRepository loadFromFile(String filename) {
        File f = new File(filename);
        if (!f.exists()) return new BugRepository();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object o = ois.readObject();
            if (o instanceof BugRepository repo) return repo;
        } catch (Exception e) {
            System.err.println("Failed to load repository: " + e.getMessage());
        }
        return new BugRepository();
    }
}