package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.service.config.ServiceConfig;
import io.vavr.collection.List;
import lombok.val;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author juraj
 */

@ContextConfiguration(classes = ServiceConfig.class)
public class MovieServiceImplTest extends AbstractTransactionalTestNGSpringContextTests {

    MovieDao movieDaoMock = mock(MovieDao.class);

    MovieService movieService;


    private Movie getFakeMovie(Long id, String name) {
        return new Movie(id, name, new HashSet<>(), new HashSet<>(), 0, Set.of(Genre.ACTION), new HashSet<>(), "test caption", "test ref");
    }


    private User getFakeUser(Long id, String name) {
        return new User(id, name, name + "@muni.cz", new HashSet<>());
    }

    @BeforeClass
    public void init() {
        movieService = new MovieServiceImpl(movieDaoMock);
    }

    @Test
    public void testFindOtherAlsoLikedMoviesReturnsEmptyIfOriginalMovieNotFound() {
        when(movieDaoMock.findById(anyLong())).thenReturn(Optional.empty());
        val similarMovies = movieService.findOtherAlsoLikedMovies(123L);
        Assert.assertTrue(similarMovies.isEmpty());
    }

    @Test
    public void testFindOtherAlsoLikedMoviesReturnsEmptyIfOnlyOriginalMovieIsLiked() {
        val m1 = getFakeMovie(1L, "m1");
        val u1 = getFakeUser(1L, "u1");
        val u2 = getFakeUser(1L, "u2");
        val u3 = getFakeUser(1L, "u3");

        new MovieRating(m1, u1, Rating.LIKED);
        new MovieRating(m1, u2, Rating.LIKED);
        new MovieRating(m1, u3, Rating.LIKED);

        when(movieDaoMock.findById(m1.getId())).thenReturn(Optional.of(m1));

        val similarMovies = movieService.findOtherAlsoLikedMovies(m1.getId());
        Assert.assertTrue(similarMovies.isEmpty());
    }

    @Test
    public void testFindOtherAlsoLikedMoviesReturnsOnlyMoviesLikedByOtherUsersOrderedByCounts() {
        val m1 = getFakeMovie(1L, "m1");
        val m2 = getFakeMovie(2L, "m2");
        val m3 = getFakeMovie(3L, "m3");
        val m4 = getFakeMovie(4L, "m4");

        val u1 = getFakeUser(1L, "u1");
        val u2 = getFakeUser(2L, "u2");
        val u3 = getFakeUser(3L, "u3");
        val u4 = getFakeUser(4L, "u4");
        val u5 = getFakeUser(5L, "u5");
        val u6 = getFakeUser(6L, "u6");
        val u7 = getFakeUser(7L, "u7");

        //m1 expected not present in output
        new MovieRating(m1, u1, Rating.LIKED);
        new MovieRating(m1, u2, Rating.LIKED);
        new MovieRating(m1, u3, Rating.LIKED);
        new MovieRating(m1, u4, Rating.LIKED);

        //m2 expected as second element
        new MovieRating(m2, u2, Rating.LIKED);
        new MovieRating(m2, u3, Rating.LIKED);

        //m3 liked by largest subset of m1 likers thus expect as first element
        new MovieRating(m3, u2, Rating.LIKED);
        new MovieRating(m3, u3, Rating.LIKED);
        new MovieRating(m3, u4, Rating.LIKED);
        new MovieRating(m3, u5, Rating.LIKED);
        new MovieRating(m3, u6, Rating.LIKED);

        //m4 only liekd by users who didint rate m1 thus not expected in output
        new MovieRating(m4, u7, Rating.LIKED);
        new MovieRating(m4, u6, Rating.LIKED);
        new MovieRating(m4, u5, Rating.LIKED);

        when(movieDaoMock.findById(m1.getId())).thenReturn(Optional.of(m1));

        val similarMovies = movieService.findOtherAlsoLikedMovies(m1.getId());
        Assert.assertEquals(similarMovies, List.of(m3, m2));
    }


    @Test
    public void testFindOtherAlsoLikedMoviesIgnoresDislikedMovies() {
        val m1 = getFakeMovie(1L, "m1");
        val m2 = getFakeMovie(2L, "m2");
        val m3 = getFakeMovie(3L, "m3");
        val m4 = getFakeMovie(4L, "m4");

        val u1 = getFakeUser(1L, "u1");
        val u2 = getFakeUser(2L, "u2");
        val u3 = getFakeUser(3L, "u3");
        val u4 = getFakeUser(4L, "u4");
        val u5 = getFakeUser(5L, "u5");
        val u6 = getFakeUser(6L, "u6");
        val u7 = getFakeUser(7L, "u7");

        //m1 expected not present in output
        new MovieRating(m1, u1, Rating.LIKED);
        new MovieRating(m1, u2, Rating.LIKED);
        new MovieRating(m1, u3, Rating.LIKED);
        new MovieRating(m1, u4, Rating.LIKED);
        new MovieRating(m1, u5, Rating.LIKED);
        new MovieRating(m1, u6, Rating.LIKED);

        //m2 expected as second element
        new MovieRating(m2, u2, Rating.LIKED);
        new MovieRating(m2, u3, Rating.LIKED);
        new MovieRating(m2, u4, Rating.LIKED);

        //m3 more ratings less LIKEdD in total so expected second
        new MovieRating(m3, u2, Rating.LIKED);
        new MovieRating(m3, u3, Rating.LIKED);
        new MovieRating(m3, u4, Rating.DISLIKED);
        new MovieRating(m3, u5, Rating.DISLIKED);
        new MovieRating(m3, u6, Rating.DISLIKED);

        //m4 only disliked so not expected at all in output
        new MovieRating(m4, u4, Rating.DISLIKED);
        new MovieRating(m4, u5, Rating.DISLIKED);
        new MovieRating(m4, u6, Rating.DISLIKED);

        when(movieDaoMock.findById(m1.getId())).thenReturn(Optional.of(m1));

        val similarMovies = movieService.findOtherAlsoLikedMovies(m1.getId());
        Assert.assertEquals(similarMovies, List.of(m2, m3));
    }

    @Test
    public void testFindOtherAlsoLikedMoviesIgnoresUsersThatDislikeOriginalMovie() {
        val m1 = getFakeMovie(1L, "m1");
        val m2 = getFakeMovie(2L, "m2");
        val m3 = getFakeMovie(3L, "m3");
        val m4 = getFakeMovie(4L, "m4");

        val u1 = getFakeUser(1L, "u1");
        val u2 = getFakeUser(2L, "u2");
        val u3 = getFakeUser(3L, "u3");
        val u4 = getFakeUser(4L, "u4");
        val u5 = getFakeUser(5L, "u5");
        val u6 = getFakeUser(6L, "u6");
        val u7 = getFakeUser(7L, "u7");

        //m1 expected not present in output
        new MovieRating(m1, u1, Rating.LIKED);
        new MovieRating(m1, u2, Rating.LIKED);
        new MovieRating(m1, u3, Rating.LIKED);
        new MovieRating(m1, u4, Rating.LIKED);
        new MovieRating(m1, u5, Rating.DISLIKED);
        new MovieRating(m1, u6, Rating.DISLIKED);
        new MovieRating(m1, u7, Rating.DISLIKED);

        //m2 Only liked by users who liked the original
        new MovieRating(m2, u2, Rating.LIKED);
        new MovieRating(m2, u3, Rating.LIKED);
        new MovieRating(m2, u4, Rating.LIKED);

        //m3 more likes in total but only 2 users who liked the original so expected second
        new MovieRating(m3, u2, Rating.LIKED);
        new MovieRating(m3, u3, Rating.LIKED);
        new MovieRating(m3, u5, Rating.LIKED);
        new MovieRating(m3, u6, Rating.LIKED);
        new MovieRating(m3, u7, Rating.LIKED);

        //m4 only liked by users who disliked m1 thus not expected in output
        new MovieRating(m4, u5, Rating.LIKED);
        new MovieRating(m4, u6, Rating.LIKED);
        new MovieRating(m4, u7, Rating.LIKED);

        when(movieDaoMock.findById(m1.getId())).thenReturn(Optional.of(m1));

        val similarMovies = movieService.findOtherAlsoLikedMovies(m1.getId());
        Assert.assertEquals(similarMovies, List.of(m2, m3));
    }

}