package be.kdg.blog.repository;

import be.kdg.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * week 4: repo-class voor het Object User
 * moet JpaRepository extenden!
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByName(String name); //custom zoek methode (hibernate maakt de gewenste query voor ons om te zoeken op naam)
}
