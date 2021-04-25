package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Movie;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author juraj
 */

public interface MovieService {
    List<Movie> findOtherAlsoLikedMovies(Long movieId);

    Optional<Movie> findMovieById(Long movieId);

    List<Movie> getRecommendedMoviesForUser(Long userId);
}
