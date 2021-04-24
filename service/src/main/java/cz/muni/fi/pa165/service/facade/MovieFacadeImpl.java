package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.facade.MovieFacade;
import cz.muni.fi.pa165.dto.MovieDto;
import cz.muni.fi.pa165.service.MovieService;
import cz.muni.fi.pa165.service.converter.BeanConverter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    final static Logger logger = LoggerFactory.getLogger(MovieFacadeImpl.class);

    @Override
    public Iterable<MovieDto> findOtherAlsoLikedMovies(Long movieId) {
        return null;
    }

    @Override
    public Optional<MovieDto> findMovieByName(String name) {
        return Optional.empty();
    }


    @Override
    public Optional<MovieDto> findMovieById(Long id) {
        logger.debug("calling findmovie by id");
        val movie = movieService.findMovieById(id);
        return movie.map(m -> converter.convert(m, MovieDto.class));
    }
}

