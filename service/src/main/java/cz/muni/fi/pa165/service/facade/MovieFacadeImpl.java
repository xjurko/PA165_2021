package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.CreateMovieDto;
import cz.muni.fi.pa165.dto.MovieDto;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.facade.MovieFacade;
import cz.muni.fi.pa165.service.MovieService;
import cz.muni.fi.pa165.service.converter.BeanConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author juraj
 */

@RequiredArgsConstructor
@Service
@Transactional
public class MovieFacadeImpl implements MovieFacade {

    final MovieService movieService;
    final BeanConverter converter;

    @Override
    public List<MovieDto> findRecommendedMoviesBasedOnMovie(Long movieId) {
        return converter.convert(movieService.findRecommendedMoviesBasedOnMovie(movieId), MovieDto.class);
    }

    @Override
    public List<MovieDto> findRecommendedMoviesForUser(Long userId) {
        return converter.convert(movieService.findRecommendedMoviesForUser(userId), MovieDto.class);
    }

    @Override
    public Optional<MovieDto> getMovieById(Long id) {
        return movieService.findMovieById(id).map(m -> converter.convert(m, MovieDto.class));
    }

    //TODO for M3 or M4 implement fuzzy matching based on levenshtein or jaro-winkler distance to return multiple movies for the search bar autofill
    @Override
    public List<MovieDto> findMoviesByName(String name) {
        return converter.convert(movieService.findMoviesByName(name), MovieDto.class);
    }

    @Override
    public Long createMovie(CreateMovieDto movie) {
        return movieService.createMovie(converter.convert(movie, Movie.class));
    }

    @Override
    public void removeMovie(Long movieId) {
        movieService.deleteMovie(movieId);
    }
}

