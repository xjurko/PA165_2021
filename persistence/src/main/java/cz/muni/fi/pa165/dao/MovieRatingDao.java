package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.MovieRating;
import cz.muni.fi.pa165.entity.RatingId;

import java.util.List;
import java.util.Optional;

/**
 * @author juraj
 *
 * DAO for manipualting user ratings
 */
public interface MovieRatingDao {
    /**
     * Creates new rating
     * @param rating rating to be persisted
     */
    void store(MovieRating rating);

    /**
     * Finds rating by its id
     * @param raitingId given id
     * @return rating with given id if found
     */
    Optional<MovieRating> findById(RatingId raitingId);

    /**
     * removes a rating from storage
     * @param rating rating to be removed
     */
    void remove(MovieRating rating);
}
