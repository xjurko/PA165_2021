package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.MovieRating;
import cz.muni.fi.pa165.entity.Rating;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.text.similarity.SimilarityScore;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Locale;
import java.util.Optional;


/**
 * @author juraj
 */

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieDao movieDao;
    private final UserDao userDao;
    private final SimilarityScore<Double> similarityMatcher;

    private final boolean similarityReversed;

    @Inject
    MovieServiceImpl(
        MovieDao movieDao,
        UserDao userDao,
        SimilarityScore<Double> similarity
    ) {
        this.movieDao = movieDao;
        this.userDao = userDao;
        this.similarityMatcher = similarity;
        this.similarityReversed = isSimilarityReversed();
    }

    private boolean isSimilarityReversed() {
        val s1 = similarityMatcher.apply("abcd", "xyzq");
        val s2 = similarityMatcher.apply("xxxx", "xxxx");
        return s2 < s1;
    }

    private Vector<Movie> getRecommendedMoviesBasedOnMovie(Movie movie) {
        return Vector.ofAll(movie.getRatings())
            .filter(r -> r.getRating() == Rating.LIKED)
            .flatMap(mrating ->
                Vector.ofAll(mrating.getUser().getMovieRatings())
                    .filter(r -> r.getRating() == Rating.LIKED)
                    .map(MovieRating::getMovie)
            );
    }

    private List<Movie> sortAndCountMoviesByFrequency(Vector<Movie> movies) {
        return movies.groupBy(x -> x)
            .map((mov, movs) -> Tuple.of(mov, movs.length()))
            .toList()
            .sortBy(movCounts -> movCounts._1.getName())
            .sortBy(movCounts -> movCounts._2)
            .map(movCounts -> movCounts._1)
            .reverse();
    }

    //    TODO: currently deals in absolute values - potentially to be changed to like/dislike ratio
    @Override
    public java.util.List<Movie> findRecommendedMoviesBasedOnMovie(Long movieId) {
        val recommendedMovies = Option.ofOptional(movieDao.findById(movieId))
            .toVector()
            .flatMap(movie -> getRecommendedMoviesBasedOnMovie(movie).removeAll(movie));

        return sortAndCountMoviesByFrequency(recommendedMovies).asJava();
    }

    @Override
    public Optional<Movie> findMovieById(Long movieId) {
        return movieDao.findById(movieId);
    }

    @Override
    public java.util.List<Movie> findRecommendedMoviesForUser(Long userId) {
        val recommendedMovies = Option.ofOptional(userDao.findById(userId))
            .toVector()
            .flatMap(user -> {
                val ratedMovies = Vector.ofAll(user.getMovieRatings())
                    .map((rating) -> new Tuple2<>(rating.getRating(), rating.getMovie()))
                    .groupBy(movieRating -> movieRating._1);
                val likedMovies = ratedMovies.getOrElse(Rating.LIKED,Vector.empty()).map(Tuple2::_2);
                val dislikedMovies = ratedMovies.getOrElse(Rating.DISLIKED,Vector.empty()).map(Tuple2::_2);

                return likedMovies
                    .flatMap(this::getRecommendedMoviesBasedOnMovie)
                    .removeAll(likedMovies)
                    .removeAll(dislikedMovies);
            });

        return sortAndCountMoviesByFrequency(recommendedMovies).asJava();
    }

    private Double getSimilarityMatcher(String movieName, String searchTerm) {
        val tokens = Array.of(movieName.split("\\s"));
        val modifier = movieName.toLowerCase().contains(searchTerm.toLowerCase()) ? 0.5 : 0.0;
        return
            tokens.map(token -> similarityMatcher.apply(token.toLowerCase(), searchTerm.toLowerCase()))
                .max()
                .getOrElse(0.0)
                + modifier;
    }

    @Override
    public java.util.List<Movie> findMoviesByName(String searchTerm) {
        val movies = Vector.ofAll(movieDao.fetchAll()).sortBy(m -> getSimilarityMatcher(m.getName(), searchTerm));
        if (similarityReversed) {
            return movies.take(10).asJava();
        } else {
            return movies.reverse().take(10).asJava();
        }
    }

    @Override
    public Long createMovie(Movie movie) {
        movieDao.store(movie);
        return movie.getId();
    }

    @Override
    public void deleteMovie(Long movieId) {
        movieDao.findById(movieId).ifPresent(movieDao::remove);
    }

    @Override
    public java.util.List<Movie> fetchMovies(Integer page, Integer pageSize) {
        return movieDao.fetchPage(page, pageSize);
    }
}
