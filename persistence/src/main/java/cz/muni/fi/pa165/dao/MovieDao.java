package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Movie;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * DAO for operations with the Movie entity
 *
 * @author juraj
 */

@Repository
public interface MovieDao {

    /**
     * create a single movie
     *
     * @param m movie to be persisted
     */
    void store(Movie m);

    /**
     * Fetch all movies
     *
     * @return subset of movies stored
     * @param page page number / offset
     * @param pageSize results set size
     */
    List<Movie> fetchPage(Integer page, Integer pageSize);

    /**
     * Find all movies containing substring in name
     *
     * @param name substring that is contained in the name
     * @return movies matching the name
     */
    List<Movie> findByName(String name);


    /**
     * Fetch all movies
     *
     * @return all movies in the system
     */
    List<Movie> fetchAll();


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
