package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.dao.MovieRatingDao;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author alia
 */

@Service
@RequiredArgsConstructor
public class MovieRatingServiceImpl implements MovieRatingService {

    final MovieRatingDao movieRatingDao;
    final UserDao userDao;
    final MovieDao movieDao;

    private User getUserOrThrow(Long userId) {
        return userDao.findById(userId).orElseThrow(() ->
            new DataRetrievalFailureException(String.format("no user with id %d", userId))
        );
    }

    private Movie getMovieOrthrow(Long movieId) {
        return movieDao.findById(movieId).orElseThrow(() ->
            new DataRetrievalFailureException(String.format("no movie with id %d", movieId))
        );
    }

    @Override
    public MovieRating setRating(Rating rating, Long userId, Long movieId) {

        Movie movie = getMovieOrthrow(movieId);
        User user = getUserOrThrow(userId);

        MovieRating movieRating = new MovieRating(movie, user, rating);
        movieRatingDao.store(movieRating);
        return movieRating;
    }

    @Override
    public List<MovieRating> findRatingsForMovie(Long movieId) {
        return new ArrayList<>(getMovieOrthrow(movieId).getRatings());
    }

    @Override
    public List<MovieRating> findRatingsByUser(Long userId) {
        return new ArrayList<>(getUserOrThrow(userId).getMovieRatings());
    }

    @Override
    public Optional<MovieRating> findRatingByUserAndMovie(Long userId, Long movieId) {
        return movieRatingDao.findById(new RatingId(movieId, userId));
    }

    @Override
    public void deleteRating(Long userId, Long movieId) {
        findRatingByUserAndMovie(userId, movieId).orElseThrow(() ->
            new DataRetrievalFailureException(String.format("no rating for user with id %d and movie with id %d", userId, movieId))
        );
    }
}
