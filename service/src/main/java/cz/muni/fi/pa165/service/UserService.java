package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.User;
import java.util.List;
import java.util.Optional;

/**
 * @author lsolodkova
 */

public interface UserService {
    Optional<User> findById(Long id);
    Optional<User> findByName(String name);
    List<User> getAll();
    void registerUser(User u, String unencryptedPassword);
    boolean authenticate(User u, String password);
    boolean isAdmin(User u);
}
