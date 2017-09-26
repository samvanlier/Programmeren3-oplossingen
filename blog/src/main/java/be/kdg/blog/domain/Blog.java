package be.kdg.blog.domain;

import java.util.ArrayList;
import java.util.Arrays;

public class Blog {
    private ArrayList<Entry> entries;

    public Blog() {
        entries = new ArrayList<>();
    }

    //maakt een paar enties aan voor blog te vullen
    public void vulBlog() {
        Entry e1 = new Entry("Naar de bakker geweest", "Was wel leuk...");
        Entry e2 = new Entry("Netflix gekeken", "Moet je ook eens doen!");
        Entry e3 = new Entry("Oefeningetjes Prog 3 gemaakt", "Ging vlotjes");
        entries.addAll(Arrays.asList(e1, e2, e3));
    }

    //get all entries
    public ArrayList<Entry> getEntries() {
        return entries;
    }

    //get 1 entry
    public Entry getEntry(int id) {
        return entries.get(id);
    }

    //add entry
    public void addEntry(String subject, String message) {
        entries.add(new Entry(subject, message));
    }

}
