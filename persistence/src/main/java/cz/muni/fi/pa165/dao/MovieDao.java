package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieDao {
    void store(Movie m);

    List<Movie> fetchAll();

    List<Movie> findByName(String name);

    Optional<Movie> findById(Long id);

    void remove(Movie m);
}
