package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.User;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

/**
 * @author lsolodkova
 */

public interface UserService {
    Optional<User> findById(Long id);
    Optional<User> findByName(String name);
    List<User> getAllUsers();
    User registerUser(String name, String email, String rawPassword) throws ValidationException;
    boolean authenticate(User u, String password);
    boolean isAdmin(User u);
    void removeUser(User u);
}
