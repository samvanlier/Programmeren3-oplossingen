package be.kdg.blog.model;

import javax.persistence.*;

@Entity //persistencie (week 4)
public class User {

    @Id //persistencie (week 4)
    @GeneratedValue (strategy = GenerationType.IDENTITY) //persistencie (week 4)
    private int id;

    @Column (length = 50, nullable = false) //persistencie (week 4)
    private String name;

    public User() {
    }

    public User(String userName) {
        this.name = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
