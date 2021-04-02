package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.User;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {
    @Override
    public void store(User m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> fetchAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> findById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Long id) {
        throw new UnsupportedOperationException();
    }
}
