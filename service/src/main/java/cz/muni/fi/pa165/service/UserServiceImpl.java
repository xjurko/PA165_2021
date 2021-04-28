package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    final UserDao userDao;

    private final PasswordEncoder encoder = new Argon2PasswordEncoder();

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
    public void registerUser(User u, String unencryptedPassword) {
        u.setPasswordHash(encoder.encode(unencryptedPassword));
        userDao.store(u);
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
