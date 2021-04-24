package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Movie;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author juraj
 */

@Service
public class MovieServiceImpl implements MovieService {

    public List<Movie> findOtherAlsoLikedMovies(Long movieId) {
        return null;
    }

    @Override
    public Optional<Movie> findMovieById(Long movieId) {
        return Optional.empty();
    }
}
