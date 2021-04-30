package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dto.CreateMovieDto;
import cz.muni.fi.pa165.dto.MovieDto;
import cz.muni.fi.pa165.entity.Movie;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for operations with movies
 *
 * @author juraj
 */
public interface MovieService {
    /**
     * Finds movies that are recommended based on the ratings this movie received from other user
     *
     * @param movieId ID of movie on which the recommendations are based on
     * @return List of movies ordered from most recommended to least
     */
    List<Movie> findRecommendedMoviesBasedOnMovie(Long movieId);


    /**
     * Fetches specific movie by id if it exists
     *
     * @param movieId id of movie to fetch
     * @return Optionally the movie with given id
     */
    Optional<Movie> findMovieById(Long movieId);

    /**
     * Finds movies recommended for a user based on which movies they have seen already and which movies were liked
     * by people who liked the same movies as this user
     *
     * @param userId Id of user for which to generate recommendations
     * @return List of movies ordered from most recommended to least
     */
    List<Movie> findRecommendedMoviesForUser(Long userId);

    /**
     * Returns movies taht contain the substring in their name
     *
     * @param name substring that is searched for in the movies names
     * @return list of all movies that contain the substring in their name
     */
    List<Movie> findMoviesByName(String name);


    /**
     * Creates new movie
     *
     * @param movie movie to be created
     * @return Id of newly generated Movie
     */
    Long createMovie(Movie movie);

    /**
     * Deletes a movie
     *
     * @param movieId id of movie to be deleted
     */
    void deleteMovie(Long movieId);
}
