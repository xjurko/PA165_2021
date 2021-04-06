package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.MovieRating;

/**
 * @author juraj
 *
 * DAO for manipualting user ratings
 */
public interface MovieRatingDao {
    /**
     * Creates new rating
     * @param rating rating to be updated
     */
    void store(MovieRating rating);

    /**
     * removes a rating from storage
     * @param rating rating to be removed
     */
    void remove(MovieRating rating);
}
