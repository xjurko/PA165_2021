package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
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
