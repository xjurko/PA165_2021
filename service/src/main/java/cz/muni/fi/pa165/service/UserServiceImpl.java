package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataRetrievalFailureException;
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
    final PasswordEncoder encoder;

    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public Optional<User> findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public User registerUser(String name, String email, String rawPassword) throws ValidationException {
        // username, email and password shouldn't be blank or null
        // finer validation needed
        Pattern regexUsername = Pattern.compile("^[a-zA-Z]([._-](?![._-])|[a-zA-Z0-9]){5,15}[a-zA-Z0-9]$");
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
    public boolean authenticate(User user, String password) {
        return encoder.matches(password, user.getPasswordHash());
    }

    @Override
    public boolean isAdmin(User user) {
        Optional<User> found = findById(user.getId());
        if (found.isEmpty()) return false;
        else return found.get().isAdmin();
    }

    @Override
    public void removeUser(User user){
        User found = findById(user.getId()).orElseThrow(()->new DataRetrievalFailureException(String.format("no user with id %d", user.getId())));
        userDao.remove(found);
    }
}
