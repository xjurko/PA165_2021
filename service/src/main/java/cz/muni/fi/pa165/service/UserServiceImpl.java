package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lsolodkova
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    final UserDao userDao;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public Optional<User> findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.fetchAll();
    }

    @Override
    public User registerUser(String name, String email, String rawPassword) throws ValidationException {
        // username, email and password shouldn't be blank or null
        // finer validation needed
        Pattern regexUsername = Pattern.compile("^[A-Za-z]\\\\w{5,15}$");
        Pattern regexEmail = Pattern.compile(".+@.+\\....?");
        if(name.isBlank() || email.isBlank()) throw new ValidationException("Username and email should be present");
        if(!regexUsername.matcher(name).matches()) throw new ValidationException("Invalid username: see the rules for usernames");
        if(!regexEmail.matcher(email).matches()) throw new ValidationException("Wrong email string");
        if(rawPassword.isBlank()) throw new ValidationException("A password should be present");
        // we need to check if such credentials already exist in the DB
        if(userDao.findByName(name).isPresent()) throw new ValidationException("This username is already occupied");
        if(userDao.findByEmail(email).isPresent()) throw new ValidationException("This email has been already registered");
        User newUser = new User(name, email, encoder.encode(rawPassword));
        userDao.store(newUser);
        return newUser;
    }

    @Override
    public boolean authenticate(User u, String password) {
        return encoder.matches(password, u.getPasswordHash());
    }

    @Override
    public boolean isAdmin(User u) {
        Optional<User> user = findById(u.getId());
        if (user.isEmpty()) return false;
        else return user.get().isAdmin();
    }

    @Override
    public void removeUser(User u){
        userDao.remove(u);
    }
}
