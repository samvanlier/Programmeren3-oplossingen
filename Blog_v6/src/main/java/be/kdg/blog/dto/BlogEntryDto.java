package be.kdg.blog.dto;

import java.sql.Timestamp;

public class BlogEntryDto {
    private String subject;
    private String message;
    private Timestamp tijdVanToevoeging;
    private String userName;

    public BlogEntryDto() {
        //default constructor
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTijdVanToevoeging() {
        return tijdVanToevoeging;
    }

    public void setTijdVanToevoeging(Timestamp tijdVanToevoeging) {
        this.tijdVanToevoeging = tijdVanToevoeging;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
