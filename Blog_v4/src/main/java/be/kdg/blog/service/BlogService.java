package be.kdg.blog.service;


import be.kdg.blog.model.Entry;
import be.kdg.blog.model.User;
import be.kdg.blog.repository.EntryRepository;
import be.kdg.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;

@Service
@Transactional
public class BlogService {

    private final EntryRepository entryRepo;
    private final UserRepository userRepo;

    @Autowired
    public BlogService(EntryRepository entryRepo, UserRepository userRepo) {
        this.entryRepo = entryRepo;
        this.userRepo = userRepo;

        vulBlog(); //vergelijkbaar met Seed() in .net
    }

    /**
     * vult de DB op met test-data
     */
    private void vulBlog() {
        User user = new User("Sam Van Lier");

        userRepo.save(user);

        Entry e1 = new Entry(
                1,
                "Naar de bakker geweest",
                "Was wel leuk...",
                Timestamp.valueOf(
                        LocalDateTime.of(LocalDate.of(2012, 1, 12), LocalTime.now())
                ),
                user

        );

        Entry e2 = new Entry(
                2,
                "Netflix gekeken",
                "Moet je ook eens doen!",
                Timestamp.valueOf(
                        LocalDateTime.of(LocalDate.of(2010, 1, 12), LocalTime.now())
                ),
                user
        );

        Entry e3 = new Entry(
                3, "Oefeningetjes Prog 3 gemaakt",
                "Ging vlotjes",
                Timestamp.valueOf(
                        LocalDateTime.of(LocalDate.of(2030, 8, 24), LocalTime.now())
                ),
                user
        );

        entryRepo.save(Arrays.asList(e1, e2, e3));
    }

    public boolean userExist(String userName) {
        User userToCheck = userRepo.findByName(userName);

        if (userToCheck == null) {
            return false;
        } else {
            return true;
        }

    }

    public void addEntry(Entry entry) {
       entryRepo.save(entry);
    }

    public Collection<Entry> getAllEntries() {
        return entryRepo.findAll();
    }

    public void addEntry(Entry entry, String userName) {
        if (userExist(userName)){
            entry.setUser(userRepo.findByName(userName));
            entryRepo.save(entry);
        }
        else {
            User user = new User(userName);

            entry.setUser(user);

            userRepo.save(user);
            entryRepo.save(entry);
        }
    }
}
