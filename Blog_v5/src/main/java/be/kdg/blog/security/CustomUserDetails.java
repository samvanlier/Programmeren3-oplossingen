package be.kdg.blog.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

//deze klassen is onze UserDetails class
//is een security principal (stap 3 van deel 1 oefening)
//wikipedia: https://en.wikipedia.org/wiki/Principal_(computer_security)

//extends User (is van de import org.springframework.security.core.userdetails.User)
public class CustomUserDetails extends User {

    private int id;

    public CustomUserDetails(String username, String password, int id, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
