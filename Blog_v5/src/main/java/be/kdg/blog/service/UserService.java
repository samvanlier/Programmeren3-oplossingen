package be.kdg.blog.service;

import be.kdg.blog.model.User;
import be.kdg.blog.repository.UserRepository;
import be.kdg.blog.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//lever de UserDetails (voor ons is dat de klasse CustomUserDatails)
@Service
public class UserService implements UserDetailsService {

    //gebruik repo om users op te halen van de db
    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepo.findByName(username);

        if (user != null) {
            return this.makeUserDetails(user);
        } else {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
    }

    /*
    deze methode gaat de userdetails maken
     */
    private CustomUserDetails makeUserDetails(User user) {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER")); //autoriteit geven aan users -> dit gaat er voorzorgen dat ze bepaalde html pagina's kunnen zien als ze ingelogt zijn
        return new CustomUserDetails(user.getName(), user.getPassword(), user.getId(), authorities); //maak een nieuw CustomUserDetails op basis van de username, password en autoriteit (in dit geval is er maar 1 rol)
    }
}
