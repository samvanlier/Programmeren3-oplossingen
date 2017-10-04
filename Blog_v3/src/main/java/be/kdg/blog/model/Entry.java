package be.kdg.blog.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class Entry implements Comparable {
    private int id;
    @NotNull(message = "{form.invalid.empty}")
    @Size(min = 3, message = "{form.invalid.size}")
    private String subject;

    @NotNull(message = "{form.invalid.empty}")
    @Size(min = 3, message = "{form.invalid.size}")
    private String message;
    private LocalDateTime tijdVanToevoeging;

    private User user;

    public Entry() {
    }

    public Entry(int id, String subject, String message) {
        this.id = id;
        this.subject = subject;
        this.message = message;
    }

    public Entry(String subject, String message) {
        this.subject = subject;
        this.message = message;
    }

    public Entry(int id, String subject, String message, LocalDateTime tijdVanToevoeging) {
        this.id = id;
        this.subject = subject;
        this.message = message;
        this.tijdVanToevoeging = tijdVanToevoeging;
    }

    public Entry(String subject, String message, String userName) {
        this.subject = subject;
        this.message = message;
        this.user = new User(userName);
    }

    public Entry(int id, String subject, String message, LocalDateTime tijdVanToevoeging, String userName) {
        this.id = id;
        this.subject = subject;
        this.message = message;
        this.tijdVanToevoeging = tijdVanToevoeging;
        this.user = new User(userName);
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTijdVanToevoeging() {
        return tijdVanToevoeging;
    }

    public void setTijdVanToevoeging(LocalDateTime tijdVanToevoeging) {
        this.tijdVanToevoeging = tijdVanToevoeging;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public int compareTo(Object o) {
        int compareDate = this.getTijdVanToevoeging().compareTo(((Entry) o).getTijdVanToevoeging());

        return compareDate;
    }
}
