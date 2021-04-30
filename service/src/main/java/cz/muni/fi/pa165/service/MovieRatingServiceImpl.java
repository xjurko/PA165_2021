package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.dao.MovieRatingDao;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.dto.Rating;
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

    private User checkUser(Long userId) {
        Optional<User> user = userDao.findById(userId);
        if (user.isEmpty())
            throw new DataRetrievalFailureException(
                    String.format("no user with id %d", userId)
            );
        return user.get();
    }

    private Movie checkMovie(Long movieId) {
        Optional<Movie> movie = movieDao.findById(movieId);
        if (movie.isEmpty())
            throw new DataRetrievalFailureException(
                    String.format("no movie with id %d", movieId)
            );
        return movie.get();
    }

    @Override
    public MovieRating setRating(Rating rating, Long userId, Long movieId) {

        cz.muni.fi.pa165.entity.Rating convertedRating = cz.muni.fi.pa165.entity.Rating.LIKED;
        if (rating == Rating.DISLIKED) {
            convertedRating = cz.muni.fi.pa165.entity.Rating.DISLIKED;
        }

        Movie movie = checkMovie(movieId);
        User user = checkUser(userId);

        MovieRating movieRating = new MovieRating(movie, user, convertedRating);
        movieRatingDao.store(movieRating);
        return movieRating;
    }

    @Override
    public List<MovieRating> findRatingsByMovie(Long movieId) {
        return new ArrayList<>(checkMovie(movieId).getRatings());
    }

    @Override
    public List<MovieRating> findRatingsByUser(Long userId) {
        return new ArrayList<>(checkUser(userId).getMovieRatings());
    }

    @Override
    public Optional<MovieRating> findRatingByUserAndMovie(Long userId, Long movieId) {
        return movieRatingDao.findById(new RatingId(movieId, userId));
    }

    @Override
    public void deleteRating(Long userId, Long movieId) {
        Optional<MovieRating> movieRating = findRatingByUserAndMovie(userId, movieId);
        if (movieRating.isEmpty())
            throw new DataRetrievalFailureException(
                    String.format("no rating for user with id %d and movie with id %d", userId, movieId)
            );
        movieRatingDao.remove(movieRating.get());
    }
}
