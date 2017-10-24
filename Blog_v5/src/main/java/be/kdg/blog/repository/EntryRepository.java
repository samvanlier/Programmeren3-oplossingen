package be.kdg.blog.repository;

import be.kdg.blog.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * week 4: repo-class voor het object Entry
 * moet JpaRepository extenden!
 */
public interface EntryRepository extends JpaRepository<Entry, Integer> {
    //mag leeg zijn (de JpaRepo genereerd defauld CRUD operaties)
}
