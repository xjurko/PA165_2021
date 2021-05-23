package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CreateMovieDto;
import cz.muni.fi.pa165.dto.MovieDto;

import java.util.List;
import java.util.Optional;

/**
 * @author juraj
 * <p>
 * Interface for interacting with Movies
 */
public interface MovieFacade {
    /**
     * Finds movies that are recommended based on the ratings this movie received from other user
     *
     * @param movieId ID of movie on which the recommendations are based on
     * @return List of movies ordered from most recommended to least
     */
    List<MovieDto> findRecommendedMoviesBasedOnMovie(Long movieId);



    /**
     * Finds movies recommended for a user based on movies they have already seen and liked
     * by people who liked the same movies as this user
     *
     * @param userId Id of user for which to generate recommendations
     * @return List of movies ordered from most recommended to least
     */
    List<MovieDto> findRecommendedMoviesForUser(Long userId);

    /**
     * Returns movies that contain the substring in their name
     *
     * @param name substring that is searched for in the movies names
     * @return list of all movies that contain the substring in their name
     */
    List<MovieDto> findMoviesByName(String name);


    /**
     * Fetches specific movie by id if it exists
     *
     * @param id id of movie to fetch
     * @return Optionally the movie with given id
     */
    Optional<MovieDto> getMovieById(Long id);


    /**
     * Creates new movie
     *
     * @param movie movie to be created
     * @return Id of newly generated Movie
     */
    Long createMovie(CreateMovieDto movie);

    /**
     * Fetches movies with pagination
     *
     * @param page page number / offset
     * @param pageSize size of the result
     * @return Subset of movies
     */
    List<MovieDto>fetchMovies(Integer page, Integer pageSize);

    /**
     * Deletes a movie
     *
     * @param movieId id of movie to be deleted
     */
    void removeMovie(Long movieId);
}

