package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.dao.MovieRatingDao;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.service.config.ServiceConfig;
import io.vavr.collection.Vector;
import lombok.val;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * @author alia
 */

@ContextConfiguration(classes = ServiceConfig.class)
public class MovieRatingServiceImplTest extends AbstractTransactionalTestNGSpringContextTests {

    MovieRatingDao movieRatingDaoMock = mock(MovieRatingDao.class);
    UserDao userDaoMock = mock(UserDao.class);
    MovieDao movieDaoMock = mock(MovieDao.class);

    MovieRatingService movieRatingService;

    private final Movie movie = new Movie().withName("Seven Samurai").withId(1L);
    private User user = new User("Akira", "akira@muni.cz");

    @BeforeClass
    public void init() {
        movieRatingService = new MovieRatingServiceImpl(movieRatingDaoMock, userDaoMock, movieDaoMock);
    }

    @BeforeMethod
    public void setFields() {
        user = new User("Akira", "akira@muni.cz");
        user.setId(1L);
        Vector.of(movieRatingDaoMock, userDaoMock, movieDaoMock).forEach(Mockito::reset);
    }

    @Test
    public void testSetRatingThrowsExceptionIfUserWithIdDoesntExist() {
        Assert.assertThrows(DataAccessException.class, () -> movieRatingService.setRating(Rating.LIKED, 1L, 1L));
    }

    @Test
    public void testSetRatingThrowsExceptionIfMovieWithIdDoesntExist() {
        Assert.assertThrows(DataAccessException.class, () -> movieRatingService.setRating(Rating.LIKED, 1L, 1L));
    }

    @Test
    public void testSetRatingChangesPreviousRatingIfExisted() {
        val existingRating = new MovieRating(movie, user, Rating.LIKED);

        when(movieDaoMock.findById(anyLong())).thenReturn(Optional.of(movie));
        when(userDaoMock.findById(anyLong())).thenReturn(Optional.of(user));
        when(movieRatingDaoMock.findById(any(RatingId.class))).thenReturn(Optional.of(existingRating));

        val movieRating = movieRatingService.setRating(Rating.DISLIKED, user.getId(), movie.getId());

        verify(movieRatingDaoMock, times(1)).remove(existingRating);
        verify(movieRatingDaoMock, times(1)).store(movieRating);
    }

    @Test
    public void testDeleteRatingThrowsExceptionWhenNoRatingByMovieAndUser() {
        Assert.assertThrows(DataAccessException.class, () -> movieRatingService.deleteRating(1L, 1L));
    }
}
