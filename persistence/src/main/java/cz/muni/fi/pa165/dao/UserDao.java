package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void store(User m);

    List<User> fetchAll();

    Optional<User> findById(Long id);

    void remove(Long id);
}
