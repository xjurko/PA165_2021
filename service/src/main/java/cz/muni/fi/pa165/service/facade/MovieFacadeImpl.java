package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.facade.MovieFacade;
import cz.muni.fi.pa165.dto.MovieDto;

import java.util.Optional;

/**
 * @author juraj
 */
public class MovieFacadeImpl implements MovieFacade {
    @Override
    public Iterable<MovieDto> findOtherAlsoLikedMovies(Long movieId) {
        return null;
    }

    @Override
    public Optional<MovieDto> findMovieByName(String name) {
        return Optional.empty();
    }
}
