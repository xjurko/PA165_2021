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
import java.util.ArrayList;
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

//    private Rating convertRating(cz.muni.fi.pa165.entity.Rating rating) {
//        Rating convertedRating = Rating.LIKED;
//        if (rating == cz.muni.fi.pa165.entity.Rating.DISLIKED) {
//            convertedRating = Rating.DISLIKED;
//        }
//        return convertedRating;
//    }

    private Rating convertRatingValue(cz.muni.fi.pa165.entity.Rating rating) {
       return beanConverter.convert(rating, Rating.class);
    }
//        Rating convertedRating = Rating.LIKED;
//        if (rating == cz.muni.fi.pa165.entity.Rating.DISLIKED) {
//            convertedRating = Rating.DISLIKED;
//        }
//        return convertedRating;
//    }

    private MovieRatingDto convertMovieRating(MovieRating rating) {
        return new MovieRatingDto(rating.getMovie().getId(), rating.getUser().getId(), convertRatingValue(rating.getRating()));
    }
//
//    private Iterable<MovieRatingDto> convertList(List<MovieRating> list) {
//        List<MovieRatingDto> found = new ArrayList<>();
//        for (MovieRating movieRating : list) {
//            found.add(new MovieRatingDto(
//                    movieRating.getMovie().getId(),
//                    movieRating.getUser().getId(),
//                    convertRating(movieRating.getRating())
//            ));
//        }
//        return found;
//    }
//


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
        Optional<MovieRating> found = movieRatingService.findRatingByUserAndMovie(userId, movieId);
        if (found.isEmpty())
            return Optional.empty();
        return Optional.of(new MovieRatingDto(
                found.get().getMovie().getId(),
                found.get().getUser().getId(),
                convertRating(found.get().getRating())
        ));
    }

    @Override
    public void deleteRating(Long userId, Long movieId) {
        movieRatingService.deleteRating(userId, movieId);
    }
}
