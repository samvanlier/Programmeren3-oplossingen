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

//DEEL 2: de service-laag
/*
- deze bevat de logica voor de controllers (Bussiness laag)
- deze laag spreekt de repo's/DAL aan zodat de contoller dit niet moet doen
 */
@Service
//gaat er voorzorgen dat code behandelt wordt zoals transacties in sql (doet rollback bij runtimeExceptions,....)
@Transactional //doc: https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/transaction/annotation/Transactional.html
public class BlogService {

    private final EntryRepository entryRepo;
    private final UserRepository userRepo;

    //repo's worden geïnjecteerd
    @Autowired
    public BlogService(EntryRepository entryRepo, UserRepository userRepo) {
        this.entryRepo = entryRepo;
        this.userRepo = userRepo;
    }

    //kijkt na of user bestaat via de userRepo
    private boolean userExist(String userName) {
        User userToCheck = userRepo.findByName(userName); //is geschreven in interface (een custom methode)

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
        if (userExist(userName)) {
            entry.setUser(userRepo.findByName(userName));
            entryRepo.save(entry);
        } else {
            User user = new User(userName);

            entry.setUser(user);

            userRepo.save(user); //methode moest niet schreven worden in de repo interface (overerving van JPA)
            entryRepo.save(entry);
        }
    }
}
