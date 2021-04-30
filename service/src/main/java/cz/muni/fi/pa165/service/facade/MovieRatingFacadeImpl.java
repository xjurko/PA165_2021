package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.MovieRatingDto;
import cz.muni.fi.pa165.dto.Rating;
import cz.muni.fi.pa165.facade.MovieRatingFacade;
import cz.muni.fi.pa165.service.MovieRatingService;
import cz.muni.fi.pa165.service.converter.BeanConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author alia
 */

@RequiredArgsConstructor
@Service
@Transactional
public class MovieRatingFacadeImpl implements MovieRatingFacade {

    final MovieRatingService movieRatingService;
    final BeanConverter converter;

    @Override
    public MovieRatingDto setRating(Rating rating, Long userId, Long movieId) {
        return converter.convert(movieRatingService.setRating(rating, userId, movieId), MovieRatingDto.class);
    }

    @Override
    public Iterable<MovieRatingDto> findRatingsByMovie(Long movieId) {
        return converter.convert(movieRatingService.findRatingsByMovie(movieId), MovieRatingDto.class);
    }

    @Override
    public Iterable<MovieRatingDto> findRatingsByUser(Long userId) {
        return converter.convert(movieRatingService.findRatingsByUser(userId), MovieRatingDto.class);
    }

    @Override
    public Optional<MovieRatingDto> findRatingByUserAndMovie(Long userId, Long movieId) {
        return movieRatingService.findRatingByUserAndMovie(userId, movieId).map(movieRating ->
                converter.convert(movieRating, MovieRatingDto.class));
    }

    @Override
    public void deleteRating(Long userId, Long movieId) {
        movieRatingService.deleteRating(userId, movieId);
    }
}
