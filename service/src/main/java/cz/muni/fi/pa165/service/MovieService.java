package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author juraj
 */

@Service
public interface MovieService {
    List<Movie> findOtherAlsoLikedMovies(Long movieId);
}
