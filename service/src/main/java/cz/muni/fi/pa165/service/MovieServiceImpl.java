package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.dto.MovieDto;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import io.vavr.collection.*;

import java.util.Optional;


/**
 * @author juraj
 */

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    final MovieDao movieDao;
    final UserDao userDao;


    private List<Tuple2<Movie, Integer>> getRecommendedMoviesBasedOnMovie(Movie movie) {
        return Vector.ofAll(movie.getRatings())
            .filter(r -> r.getRating() == Rating.LIKED)
            .flatMap(mrating ->
                Vector.ofAll(mrating.getUser().getMovieRatings())
                    .filter(r -> r.getRating() == Rating.LIKED)
                    .map(urating -> urating.getMovie())
            )
            .groupBy(x -> x)
            .map((mov, movs) -> Tuple.of(mov, movs.length()))
            .remove(movie)
            .toList();
    }

    //    TODO: currently deals in absolute values - potentially to be changed to like/dislike ratio
    @Override
    public java.util.List<Movie> findRecommendedMoviesBasedOnMovie(Long movieId) {
        return Option.ofOptional(movieDao.findById(movieId))
            .toList()
            .flatMap(this::getRecommendedMoviesBasedOnMovie)
            .sortBy(movCounts -> movCounts._2)
            .map(movCounts -> movCounts._1)
            .reverse()
            .asJava();
    }

    @Override
    public Optional<Movie> findMovieById(Long movieId) {
        return movieDao.findById(movieId);
    }

    @Override
    public java.util.List<Movie> findRecommendedMoviesForUser(Long userId) {
        return Option.ofOptional(userDao.findById(userId))
            .toList()
            .flatMap(user -> {
                val likedMovies = List.ofAll(user.getMovieRatings()).map(r -> r.getMovie());
                return likedMovies.flatMap(this::getRecommendedMoviesBasedOnMovie)
                    .sortBy(movCounts -> movCounts._2)
                    .map(movCounts -> movCounts._1)
                    .removeAll(likedMovies)
                    .reverse();
            })
            .asJava();
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
}
