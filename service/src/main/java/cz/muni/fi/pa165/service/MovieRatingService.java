package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.MovieRating;
import cz.muni.fi.pa165.entity.Rating;

import java.util.List;
import java.util.Optional;

/**
 * @author alia
 *
 * MovieRating Service layer interface
 *
 */

public interface MovieRatingService {

    /**
     * Set rating for given user and movie.
     *
     * @param rating value of rating
     * @param userId id of the given user
     * @param movieId id of the given movie
     * @return newly created rating
     */
    MovieRating setRating(Rating rating, Long userId, Long movieId);

    /**
     * Find all ratings by movie
     *
     * @param movieId id of the given movie
     * @return ratings for the given movie
     */
    List<MovieRating> findRatingsByMovie(Long movieId);

    /**
     * Find all ratings by user
     *
     * @param userId id of the given user
     * @return ratings for the given user
     */
    List<MovieRating> findRatingsByUser(Long userId);

    /**
     * Find rating by user and movie
     *
     * @param userId id of the given user
     * @param movieId id of the given movie
     * @return rating for the given user and movie
     */
    Optional<MovieRating> findRatingByUserAndMovie(Long userId, Long movieId);

    /**
     * Delete a rating by user and movie
     *
     * @param userId id of the given user
     * @param movieId id of the given movie
     */
    void deleteRating(Long userId, Long movieId);
}
