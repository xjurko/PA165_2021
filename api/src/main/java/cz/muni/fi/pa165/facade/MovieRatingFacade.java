package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.MovieRatingDto;
import cz.muni.fi.pa165.dto.Rating;

import java.util.Optional;

/**
 * @author alia
 * <p>
 * MovieRating Facade layer interface
 */

public interface MovieRatingFacade {

    /**
     * Set rating for given user and movie.
     *
     * @param rating rating to be set
     * @return newly created rating
     */
    MovieRatingDto setRating(MovieRatingDto rating);

    /**
     * Find all ratings by movie
     *
     * @param movieId id of the given movie
     * @return ratings for the given movie
     */
    Iterable<MovieRatingDto> findRatingsByMovie(Long movieId);

    /**
     * Find all ratings by user
     *
     * @param userId id of the given user
     * @return ratings for the given user
     */
    Iterable<MovieRatingDto> findRatingsByUser(Long userId);

    /**
     * Find rating by user and movie
     *
     * @param userId  id of the given user
     * @param movieId id of the given movie
     * @return rating for the given user and movie
     */
    Optional<MovieRatingDto> findRatingByUserAndMovie(Long userId, Long movieId);

    /**
     * Delete a rating by user and movie
     *
     * @param userId  id of the given user
     * @param movieId id of the given movie
     */
    void deleteRating(Long userId, Long movieId);
}
