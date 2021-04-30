package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.dao.MovieRatingDao;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.MovieRating;
import cz.muni.fi.pa165.entity.Rating;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.config.ServiceConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

/**
 * @author alia
 */

@ContextConfiguration(classes = ServiceConfig.class)
public class MovieRatingServiceImplTest extends AbstractTransactionalTestNGSpringContextTests {

    MovieRatingDao movieRatingDao = mock(MovieRatingDao.class);
    UserDao userDao = mock(UserDao.class);
    MovieDao movieDao = mock(MovieDao.class);

    MovieRatingService movieRatingService;

    private MovieRating movieRating = new MovieRating();

    @BeforeClass
    public void init() {
        movieRatingService = new MovieRatingServiceImpl(movieRatingDao, userDao, movieDao);
    }

    @BeforeMethod
    public void setFields() {
        Movie movie = new Movie();
        movie.setName("Seven Samurai");
        User user = new User("Akira", "akira@muni.cz");
        movieRating = new MovieRating(movie, user, Rating.LIKED);
    }

    @Test
    public void testSetRatingThrowsExceptionWhenUserNotFound() {

    }

}
