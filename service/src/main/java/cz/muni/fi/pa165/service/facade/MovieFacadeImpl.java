package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.facade.MovieFacade;
import cz.muni.fi.pa165.dto.MovieDto;
import cz.muni.fi.pa165.dto.MovieRatingDto;

import java.util.Optional;

/**
 * @author juraj
 */
public class MovieFacadeImpl implements MovieFacade {
    @Override
    public Iterable<MovieDto> getSimilarMovies(MovieRatingDto movie) {
        return null;
    }

    @Override
    public Optional<MovieDto> findMovieByName(String name) {
        return Optional.empty();
    }
}
