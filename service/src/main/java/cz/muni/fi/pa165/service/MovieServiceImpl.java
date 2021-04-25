package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.dao.MovieRatingDao;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.MovieRating;
import io.vavr.Function1;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import io.vavr.collection.*;

import java.util.Optional;


/**
 * @author juraj
 */

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    MovieDao movieDao;
    MovieRatingDao ratingDao;

    @Override
    public java.util.List<Movie> findOtherAlsoLikedMovies(Long movieId) {
        return Option.ofOptional(movieDao.findById(movieId))
            .toList()
            .flatMap(
                m -> Vector.ofAll(m.getRatings())
                    .flatMap(mrating -> Vector.ofAll(mrating.getUser().getMovieRatings())
                        .map(urating -> urating.getMovie())
                    )
                    .groupBy(Function1.identity())
                    .map((mov, movs) -> Tuple.of(mov, movs.length()))
                    .toList()
                    .sortBy(movCounts -> movCounts._2)
                    .map(movCounts -> movCounts._1)
            ).asJava();
    }

    @Override
    public Optional<Movie> findMovieById(Long movieId) {
        return movieDao.findById(movieId);
    }
}
