package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.MovieRating;
import cz.muni.fi.pa165.entity.Rating;
import io.vavr.Tuple;
import io.vavr.collection.List;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * @author juraj
 */

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    final MovieDao movieDao;
    final UserDao userDao;


    private Vector<Movie> getRecommendedMoviesBasedOnMovie(Movie movie) {
        return Vector.ofAll(movie.getRatings())
            .filter(r -> r.getRating() == Rating.LIKED)
            .flatMap(mrating ->
                Vector.ofAll(mrating.getUser().getMovieRatings())
                    .filter(r -> r.getRating() == Rating.LIKED)
                    .map(MovieRating::getMovie)
            );
    }

    private List<Movie> sortAndCountMoviesByFrequency(Vector<Movie> movies) {
        return movies.groupBy(x -> x)
            .map((mov, movs) -> Tuple.of(mov, movs.length()))
            .toList()
            .sortBy(movCounts -> movCounts._2)
            .map(movCounts -> movCounts._1)
            .reverse();
    }

    //    TODO: currently deals in absolute values - potentially to be changed to like/dislike ratio
    @Override
    public java.util.List<Movie> findRecommendedMoviesBasedOnMovie(Long movieId) {
        val recommendedMovies = Option.ofOptional(movieDao.findById(movieId))
            .toVector()
            .flatMap(movie -> getRecommendedMoviesBasedOnMovie(movie).removeAll(movie));

        return sortAndCountMoviesByFrequency(recommendedMovies).asJava();
    }

    @Override
    public Optional<Movie> findMovieById(Long movieId) {
        return movieDao.findById(movieId);
    }

    @Override
    public java.util.List<Movie> findRecommendedMoviesForUser(Long userId) {
        val recommendedMovies = Option.ofOptional(userDao.findById(userId))
            .toVector()
            .flatMap(user -> {
                val likedMovies = Vector.ofAll(user.getMovieRatings())
                    .filter(rating -> rating.getRating() == Rating.LIKED)
                    .map(MovieRating::getMovie);
                return likedMovies.flatMap(this::getRecommendedMoviesBasedOnMovie).removeAll(likedMovies);
            });

        return sortAndCountMoviesByFrequency(recommendedMovies).asJava();
    }

    @Override
    public java.util.List<Movie> findMoviesByName(String name) {
        return movieDao.findByName(name);
    }

    @Override
    public Long createMovie(Movie movie) {
        movieDao.store(movie);
        return movie.getId();
    }

    @Override
    public void deleteMovie(Long movieId) {
        movieDao.findById(movieId).ifPresent(movieDao::remove);
    }

    @Override
    public java.util.List<Movie> fetchMovies(Integer page, Integer pageSize) {
        return movieDao.fetchPage(page, pageSize);
    }
}
