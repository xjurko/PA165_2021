package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceConfig;
import cz.muni.fi.pa165.entity.Actor;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.MovieRating;
import cz.muni.fi.pa165.entity.User;
import lombok.val;
import org.hibernate.Session;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import javax.validation.constraints.AssertTrue;
import java.util.List;
import java.util.Optional;
import java.util.Set;


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
        val user = new User("testUser1", "user1@fi.muni.cz");
        val movie = new Movie("testMovie", Set.of(), 10, Set.of(), "", "");
        val rating = new MovieRating(movie, user, 2);

        ratingDao.store(rating);

        em.flush();

        val storedUser = userDao.findById(user.getId());
        val storedMovie = movieDao.findById(movie.getId());

        Assert.assertTrue(storedUser.isPresent());
        Assert.assertEquals(storedUser, Optional.of(user));
        Assert.assertTrue(storedUser.get().getMovieRatings().contains(rating));

        Assert.assertTrue(storedMovie.isPresent());
        Assert.assertEquals(storedMovie, Optional.of(movie));
        Assert.assertTrue(storedMovie.get().getMovieRatings().contains(rating));

    }


    @Test
    public void testRemovingRatingRemovesItFromMovieAndUser() {
        val user = new User("testUser1", "user1@fi.muni.cz");
        val movie = new Movie("testMovie", Set.of(), 10, Set.of(), "", "");

        val rating = new MovieRating(movie, user, 2);
        ratingDao.store(rating);
        em.flush();

        Assert.assertFalse(user.getMovieRatings().isEmpty());
        Assert.assertFalse(movie.getMovieRatings().isEmpty());

        ratingDao.remove(rating);

        em.flush();

        val storedMovie = movieDao.findById(movie.getId());
        val storedUser = userDao.findById(user.getId());

        Assert.assertTrue(user.getMovieRatings().isEmpty());
        Assert.assertTrue(movie.getMovieRatings().isEmpty());

        Assert.assertTrue(storedUser.isPresent());
        Assert.assertEquals(storedUser.get().getMovieRatings(), user.getMovieRatings());
        Assert.assertTrue(storedUser.get().getMovieRatings().isEmpty());


        Assert.assertTrue(storedMovie.isPresent());
        Assert.assertEquals(storedMovie.get().getMovieRatings(), movie.getMovieRatings());
        Assert.assertTrue(storedMovie.get().getMovieRatings().isEmpty());
    }

    @Test
    public void testRemovingMovieRemovesAllRatings() {
        val user = new User("testUser1", "user1@fi.muni.cz");
        val movie = new Movie("testMovie", Set.of(), 10, Set.of(), "", "");

        val rating = new MovieRating(movie, user, 2);
        ratingDao.store(rating);
        em.flush();

        movieDao.remove(movie);
        em.flush();

        val storedUser = userDao.findById(user.getId()).get();
        Assert.assertTrue(storedUser.getMovieRatings().isEmpty());
    }

    @Test
    public void testRemovingUserRemovesAllRatings() {
        val user = new User("testUser1", "user1@fi.muni.cz");
        val movie = new Movie("testMovie",  Set.of(), 10, Set.of(), "", "");
        val movie2 = new Movie("testMovi2e", Set.of(), 10, Set.of(), "", "");

        val rating = new MovieRating(movie, user, 2);
        val rating2 = new MovieRating(movie2, user, 1);
        userDao.store(user);
        ratingDao.store(rating);
        ratingDao.store(rating2);
        em.flush();

        userDao.remove(user);
        em.flush();

        val storedMovie = movieDao.findById(movie.getId()).get();
        val storedMovie2 = movieDao.findById(movie2.getId()).get();
        Assert.assertTrue(storedMovie.getMovieRatings().isEmpty());
        Assert.assertTrue(storedMovie2.getMovieRatings().isEmpty());
    }

    @Test
    public void testRemovingRatingDoesntWipeUncommitedChangesFromMovie() {
        val user = new User("testUser1", "user1@fi.muni.cz");
        val movie = new Movie("testMovie",  Set.of(), 10, Set.of(), "", "");
        val movie2 = new Movie("testMovi2e", Set.of(), 10, Set.of(), "", "");

        val rating = new MovieRating(movie, user, 2);
        val rating2 = new MovieRating(movie2, user, 1);
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
