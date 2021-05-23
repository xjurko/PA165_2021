package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.CreateMovieDto;
import cz.muni.fi.pa165.dto.MovieDto;
import cz.muni.fi.pa165.entity.Actor;
import cz.muni.fi.pa165.entity.Director;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.facade.MovieFacade;
import cz.muni.fi.pa165.service.ActorService;
import cz.muni.fi.pa165.service.DirectorService;
import cz.muni.fi.pa165.service.MovieService;
import cz.muni.fi.pa165.service.converter.BeanConverter;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.dao.DataRetrievalFailureException;
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
    final DirectorService directorService;
    final ActorService actorService;
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
        val movieEntity = converter.convert(movie, Movie.class);
        val directors = Vector.ofAll(movie.getDirectorIds()).flatMap(id -> Option.ofOptional(directorService.findById(id)));
        val actors = Vector.ofAll(movie.getActorIds()).flatMap(id -> Option.ofOptional(actorService.findActorById(id)));

        val missingDirectorsIds = Vector.ofAll(movie.getDirectorIds()).toSet().diff(directors.map(Director::getId).toSet());
        val missingActorsIds = Vector.ofAll(movie.getActorIds()).toSet().diff(actors.map(Actor::getId).toSet());

        if (missingActorsIds.nonEmpty())
            throw new DataRetrievalFailureException("Couldnt create movie: couldnt find linked actors: " + missingActorsIds);
        if (missingDirectorsIds.nonEmpty())
            throw new DataRetrievalFailureException("Couldnt create movie: couldnt find linked directors: " + missingActorsIds);

        val denormalizedMovie = movieEntity.withDirectors(directors.toJavaSet()).withCast(actors.toJavaSet());
        return movieService.createMovie(denormalizedMovie);
    }

    @Override
    public List<MovieDto> fetchMovies(Integer page, Integer pageSize) {
        return converter.convert(movieService.fetchMovies(page, pageSize), MovieDto.class);
    }

    @Override
    public void removeMovie(Long movieId) {
        movieService.deleteMovie(movieId);
    }
}

