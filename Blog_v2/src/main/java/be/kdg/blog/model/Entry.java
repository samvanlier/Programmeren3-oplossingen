package be.kdg.blog.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Entry implements Comparable {
    private int id;
    @NotNull
    @Size(min = 3)
    private String subject;

    @NotNull
    @Size(min = 3)
    private String message;
    private LocalDateTime tijdVanToevoeging;

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

    @Override
    public int compareTo(Object o) {
        int compareDate = this.getTijdVanToevoeging().compareTo(((Entry) o).getTijdVanToevoeging());


        return compareDate;
    }
}
