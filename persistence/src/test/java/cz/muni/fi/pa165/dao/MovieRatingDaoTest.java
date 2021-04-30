package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceConfig;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.MovieRating;
import cz.muni.fi.pa165.entity.Rating;
import cz.muni.fi.pa165.entity.User;
import lombok.val;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


/**
 * Tests for MovieRatingDao
 *
 * @author juraj
 */

@ContextConfiguration(classes = PersistenceConfig.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class MovieRatingDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private UserDao userDao;

    @Inject
    private MovieDao movieDao;

    @Inject
    private MovieRatingDao ratingDao;

    @PersistenceContext
    private EntityManager em;


    @Test
    public void testStoreRatingPreservesNewEntities() {
        val user = new User("testUser1", "user1@fi.muni.cz", "passw0rd");
        val movie = new Movie("testMovie", 10, 2000, "", "");
        val rating = new MovieRating(movie, user, Rating.LIKED);

        ratingDao.store(rating);

        em.flush();

        val storedUser = userDao.findById(user.getId());
        val storedMovie = movieDao.findById(movie.getId());

        Assert.assertTrue(storedUser.isPresent());
        Assert.assertEquals(storedUser, Optional.of(user));
        Assert.assertTrue(storedUser.get().getMovieRatings().contains(rating));

        Assert.assertTrue(storedMovie.isPresent());
        Assert.assertEquals(storedMovie, Optional.of(movie));
        Assert.assertTrue(storedMovie.get().getRatings().contains(rating));

    }


    @Test
    public void testRemovingRatingRemovesItFromMovieAndUser() {
        val user = new User("testUser1", "user1@fi.muni.cz", "passw0rd");
        val movie = new Movie("testMovie", 10, 2000, "", "");

        val rating = new MovieRating(movie, user, Rating.LIKED);
        ratingDao.store(rating);
        em.flush();

        Assert.assertFalse(user.getMovieRatings().isEmpty());
        Assert.assertFalse(movie.getRatings().isEmpty());

        ratingDao.remove(rating);

        em.flush();

        val storedMovie = movieDao.findById(movie.getId());
        val storedUser = userDao.findById(user.getId());

        Assert.assertTrue(user.getMovieRatings().isEmpty());
        Assert.assertTrue(movie.getRatings().isEmpty());

        Assert.assertTrue(storedUser.isPresent());
        Assert.assertEquals(storedUser.get().getMovieRatings(), user.getMovieRatings());
        Assert.assertTrue(storedUser.get().getMovieRatings().isEmpty());


        Assert.assertTrue(storedMovie.isPresent());
        Assert.assertEquals(storedMovie.get().getRatings(), movie.getRatings());
        Assert.assertTrue(storedMovie.get().getRatings().isEmpty());
    }

    @Test
    public void testRemovingMovieRemovesAllRatings() {
        val user = new User("testUser1", "user1@fi.muni.cz", "passw0rd");
        val movie = new Movie("testMovie", 10, 2000, "", "");

        val rating = new MovieRating(movie, user, Rating.LIKED);
        ratingDao.store(rating);
        em.flush();

        movieDao.remove(movie);
        em.flush();

        val storedUser = userDao.findById(user.getId()).get();
        Assert.assertTrue(storedUser.getMovieRatings().isEmpty());
    }

    @Test
    public void testRemovingUserRemovesAllRatings() {
        val user = new User("testUser1", "user1@fi.muni.cz", "passw0rd");
        val movie = new Movie("testMovie", 10, 2000, "", "");
        val movie2 = new Movie("testMovi2e", 10, 2000, "", "");

        val rating = new MovieRating(movie, user, Rating.LIKED);
        val rating2 = new MovieRating(movie2, user, Rating.LIKED);
        userDao.store(user);
        ratingDao.store(rating);
        ratingDao.store(rating2);
        em.flush();

        userDao.remove(user);
        em.flush();

        val storedMovie = movieDao.findById(movie.getId()).get();
        val storedMovie2 = movieDao.findById(movie2.getId()).get();
        Assert.assertTrue(storedMovie.getRatings().isEmpty());
        Assert.assertTrue(storedMovie2.getRatings().isEmpty());
    }

    @Test
    public void testRemovingRatingDoesntWipeUncommitedChangesFromMovie() {
        val user = new User("testUser1", "user1@fi.muni.cz", "passw0rd");
        val movie = new Movie("testMovie", 10, 2000, "", "");
        val movie2 = new Movie("testMovi2e", 10, 2000, "", "");

        val rating = new MovieRating(movie, user, Rating.LIKED);
        val rating2 = new MovieRating(movie2, user, Rating.LIKED);
        userDao.store(user);
        ratingDao.store(rating);
        ratingDao.store(rating2);
        em.flush();

        movie.setCaption("testCaption");
        // intentionally not flushing here

        ratingDao.remove(rating);
        em.flush();

        val storedMovie = movieDao.findById(movie.getId()).get();
        Assert.assertEquals(storedMovie.getCaption(), "testCaption");
    }
}
