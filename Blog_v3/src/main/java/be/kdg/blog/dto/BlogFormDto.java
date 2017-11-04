package be.kdg.blog.dto;

//DEEL 3
public class BlogFormDto {
    private String subject;
    private String message;
    private String userName;

    public BlogFormDto() {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
