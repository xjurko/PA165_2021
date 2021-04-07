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
     * persists  a single entity in the database
     *
     * @param user entity to be persisted
     */
    void store(User user);

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
     * Removes a user from the database
     *
     * @param user user entity to be removed
     */
    void remove(User user);
}
