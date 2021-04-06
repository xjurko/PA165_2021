package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Movie;

import java.util.List;
import java.util.Optional;

/**
 * DAO for oeprations with the Movie entity
 *
 * @author juraj
 */

public interface MovieDao {

    /**
     * create or update a single movie
     *
     * @param m movie to be updated
     */
    void store(Movie m);

    /**
     * Fetch all movies
     *
     * @return all movies currently stored
     */
    List<Movie> fetchAll();

    /**
     * Find all movies with the given name
     *
     * @param name name of the movie
     * @return moveis matching the name
     */
    List<Movie> findByName(String name);


    /**
     * Find a single movie by its Id
     *
     * @param id id of movie
     * @return movie if it exists
     */
    Optional<Movie> findById(Long id);

    /**
     * Deletes a movie
     *
     * @param m movie to be deleted
     */
    void remove(Movie m);
}