package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.MovieRatingDto;
import cz.muni.fi.pa165.dto.Rating;
import cz.muni.fi.pa165.entity.MovieRating;
import cz.muni.fi.pa165.facade.MovieRatingFacade;
import cz.muni.fi.pa165.service.MovieRatingService;
import cz.muni.fi.pa165.service.converter.BeanConverter;
import io.vavr.collection.Vector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author alia
 */

@RequiredArgsConstructor
@Service
@Transactional
public class MovieRatingFacadeImpl implements MovieRatingFacade {

    final MovieRatingService movieRatingService;
    final BeanConverter beanConverter;

    private MovieRatingDto convertMovieRating(MovieRating rating) {
        return beanConverter.convert(rating, MovieRatingDto.class)
                .withMovieId(rating.getMovie().getId())
                .withUserId(rating.getUser().getId());
    }

    private Iterable<MovieRatingDto> convertList(List<MovieRating> ratings) {
        return Vector.ofAll(ratings).map(this::convertMovieRating);
    }

    @Override
    public MovieRatingDto setRating(MovieRatingDto rating) {
        movieRatingService.setRating(rating.getRating(), rating.getUserId(), rating.getMovieId());
        return rating;
    }

    @Override
    public Iterable<MovieRatingDto> findRatingsByMovie(Long movieId) {
        return convertList(movieRatingService.findRatingsByMovie(movieId));
    }

    @Override
    public Iterable<MovieRatingDto> findRatingsByUser(Long userId) {
        return convertList(movieRatingService.findRatingsByUser(userId));
    }

    @Override
    public Optional<MovieRatingDto> findRatingByUserAndMovie(Long userId, Long movieId) {
        return movieRatingService.findRatingByUserAndMovie(userId, movieId)
                .map(this::convertMovieRating);
    }

    @Override
    public void deleteRating(Long userId, Long movieId) {
        movieRatingService.deleteRating(userId, movieId);
    }
}
