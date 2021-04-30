package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.User;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for operations with user accounts
 * @author lsolodkova
 */

public interface UserService {
    /**
     * Search for user by id
     *
     * @param id primary key assigned to a row in database
     * @return entity of a user
     */
    Optional<User> findById(Long id);

    /**
     * Search for user by username
     *
     * @param name username
     * @return entity of a user
     */
    Optional<User> findByName(String name);

    /**
     * registers a new user in the system
     *
     * @param name username
     * @param email user's email
     * @param rawPassword plaintext password chosen by user
     * @return entity of a newly registered user
     */
    User registerUser(String name, String email, String rawPassword) throws ValidationException;

    /**
     * Checks if a user with given credentials exists in the system
     *
     * @param user entity of a user
     * @param password plaintext password provided by user
     * @return true, if such user exists and password matches the hash; otherwise false
     */
    boolean authenticate(User user, String password);

    /**
     * Checks if a user with given credentials has administrative privileges
     *
     * @param user entity of a user
     * @return true, if user exists AND has admin privileges; otherwise false
     */
    boolean isAdmin(User user);

    /**
     * Removes a particular user
     *
     * @param user entity of user to be removed
     * @return nothing on success; throws an exception if such user does not exist in the system
     */
    void removeUser(User user);
}
