package be.kdg.blog.service;


import be.kdg.blog.model.Entry;
import be.kdg.blog.model.User;
import be.kdg.blog.repository.EntryRepository;
import be.kdg.blog.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class BlogService {

    private final EntryRepository entryRepo;
    private final UserRepository userRepo;

    @Autowired
    public BlogService(EntryRepository entryRepo, UserRepository userRepo) {
        this.entryRepo = entryRepo;
        this.userRepo = userRepo;

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

    public Collection<Entry> getEntriesByUserId(int userId) throws NotFoundException {
        List<Entry> entriesByUser = new ArrayList<>();

        User user = userRepo.findOne(userId);
        if (user == null)
            throw new NotFoundException("user not found");


        for (Entry entry :
                this.getAllEntries()) {
            if (entry.getUser().getId() == userId)
                entriesByUser.add(entry);
        }

        return entriesByUser;
    }
}
