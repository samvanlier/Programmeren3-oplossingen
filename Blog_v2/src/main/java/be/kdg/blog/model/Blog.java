package be.kdg.blog.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//DEEL 3;
@Component
public class Blog {
    private ArrayList<Entry> entries; //sort of db

    public Blog() {
        entries = new ArrayList<>();
    }

    //maakt een paar enties aan voor blog te vullen
    public void vulBlog() {
        Entry e1 = new Entry(1, "Naar de bakker geweest", "Was wel leuk...", LocalDateTime.of(LocalDate.of(2012, 1, 12), LocalTime.now()));
        Entry e2 = new Entry(2, "Netflix gekeken", "Moet je ook eens doen!",  LocalDateTime.of(LocalDate.of(2010, 1, 12), LocalTime.now()));
        Entry e3 = new Entry(3, "Oefeningetjes Prog 3 gemaakt", "Ging vlotjes", LocalDateTime.of(LocalDate.of(2030, 8, 24), LocalTime.now()));
        entries.addAll(Arrays.asList(e1, e2, e3));
    }

    //get all entries
    public ArrayList<Entry> getEntries() {
        Collections.sort(entries);
        return entries;
    }

    //get 1 entry
    public Entry getEntry(int id) {
        return entries.get(id);
    }

    //add entry
    public void addEntry(String subject, String message, LocalDateTime tijd) {
        int id = getId();
        entries.add(new Entry(id, subject, message, tijd));
    }

    public void addEntry(Entry entry) {
        int id = getId();
        entry.setId(id);
        entry.setTijdVanToevoeging(LocalDateTime.now());
        entries.add(entry);
    }

    public void addEntry(String subject, String message) {
        entries.add(new Entry(getId(), subject, message, LocalDateTime.now()));
    }

    private int getId() {
        return entries.size() + 1;
    }
}
