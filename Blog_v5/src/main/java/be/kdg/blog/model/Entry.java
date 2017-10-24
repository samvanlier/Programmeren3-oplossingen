package be.kdg.blog.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity //persistencie (week 4)
public class Entry implements Comparable {
    @Id //persistencie (week 4)
    @GeneratedValue(strategy =  GenerationType.IDENTITY) //persistencie (week 4)
    @Column //persistencie (week 4)
    private int id;

    @Column //persistencie (week 4)
    @NotNull(message = "{form.invalid.empty}") //validation (week 3)
    @Size(min = 3, message = "{form.invalid.size}") //validation (week 3)
    private String subject;

    @Column //persistencie (week 4)
    @NotNull(message = "{form.invalid.empty}") //validation (week 3)
    @Size(min = 3, message = "{form.invalid.size}") //validation (week 3)
    private String message;

    @Column
    private Timestamp tijdVanToevoeging;

    @ManyToOne
    @JoinColumn (nullable = false) //werkt enkel als Class User geen naam heeft gekregen (zie de annotatie)
    private User user;

    public Entry() {
    }

    public Entry(String subject, String message, String userName) {
        this.subject = subject;
        this.message = message;
        this.user = new User(userName);
    }

    public Entry(int id, String subject, String message, Timestamp tijdVanToevoeging, User user) {
        this.id = id;
        this.subject = subject;
        this.message = message;
        this.tijdVanToevoeging = tijdVanToevoeging;
        this.user = user;
    }

    public Entry(String subject, String message) {
        this.subject = subject;
        this.message = message;
    }

    public Entry(int id, String subject, String message, Timestamp tijdVanToevoeging) {
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

    public Timestamp getTijdVanToevoeging() {
        return tijdVanToevoeging;
    }

    public void setTijdVanToevoeging(Timestamp tijdVanToevoeging) {
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
