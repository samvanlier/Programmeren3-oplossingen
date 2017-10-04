package be.kdg.blog.model;

public class User {
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
