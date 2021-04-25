package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.MovieDto;
import cz.muni.fi.pa165.dto.MovieRatingDto;

import java.util.Optional;

/**
 * @author juraj
 */
public interface MovieFacade {
    Iterable<MovieDto> findOtherAlsoLikedMovies(Long movieId);

    Optional<MovieDto> findMovieByName(String name);

    Optional<MovieDto> findMovieById(Long id);
}
