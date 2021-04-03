package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.UserMovieRating;

/**
 * @author juraj
 *
 * DAO for manipualting user ratings
 */
public interface UserMovieRatingDao {
    void store(UserMovieRating rating);

    void remove(UserMovieRating rating);
}
