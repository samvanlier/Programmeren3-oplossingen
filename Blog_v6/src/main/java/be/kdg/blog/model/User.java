package be.kdg.blog.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity //persistencie (week 4)
public class User {

    @Id //persistencie (week 4)
    @GeneratedValue (strategy = GenerationType.IDENTITY) //persistencie (week 4)
    private int id;

    @Column (length = 50, nullable = false) //persistencie (week 4)
    private String name;

    @Column(nullable = false)
    @Size(min = 3)
    private String password;

    public User() {
    }

    public User(String userName) {
        this.name = userName;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }
}
