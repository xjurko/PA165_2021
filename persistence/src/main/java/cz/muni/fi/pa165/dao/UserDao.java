package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * DAO for operations with User entity.
 * Fetches data from the table of registered users.
 *
 * @author lsolodkova
 */
public interface UserDao {
    /**
     * stores or updates a single entity in the database
     *
     * @param u entity to be updated
     */
    void store(User u);

    /**
     * retrieves all stored entities
     * @return all registered users
     */
    List<User> fetchAll();

    /**
     * Find a particular user by id
     *
     * @param id entity's id
     * @return User entity which matches the id
     */
    Optional<User> findById(Long id);

    /**
     * Find a particular user by username
     *
     * @param name username
     * @return User entity which matches the particular name
     */
    Optional<User> findByName(String name);

    /**
     * Removes a user with particular id from the database
     *
     * @param id user's id to be removed
     */
    void remove(Long id);
}
