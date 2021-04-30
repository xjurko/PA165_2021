package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.MovieRatingDto;
import cz.muni.fi.pa165.entity.Rating;

import java.util.Optional;

/**
 * @author alia
 *
 * MovieRating Facade layer interface
 *
 */

public interface MovieRatingFacade {

    /**
     * Set rating for given user and movie.
     *
     * @param rating value of rating
     * @param userId id of the given user
     * @param movieId id of the given movie
     * @return newly created rating
     */
    MovieRatingDto setRating(Rating rating, Long userId, Long movieId);

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
     * @param userId id of the given user
     * @param movieId id of the given movie
     * @return rating for the given user and movie
     */
    Optional<MovieRatingDto> findRatingByUserAndMovie(Long userId, Long movieId);

    /**
     * Delete a rating by user and movie
     *
     * @param userId id of the given user
     * @param movieId id of the given movie
     */
    void deleteRating(Long userId, Long movieId);
}
