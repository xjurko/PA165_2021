package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.dao.MovieRatingDao;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.config.ServiceConfig;
import lombok.val;
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

    private final Movie movie = new Movie();
    private User user = new User("Akira", "akira@muni.cz");

    @BeforeClass
    public void init() {
        movieRatingService = new MovieRatingServiceImpl(movieRatingDaoMock, userDaoMock, movieDaoMock);
    }

    @BeforeMethod
    public void setFields() {
        movie.setName("Seven Samurai");
        movie.setId(1L);
        user = new User("Akira", "akira@muni.cz");
        user.setId(1L);
    }

    @Test
    public void testSetRatingThrowsExceptionIfUserIsEmpty() {
        when(userDaoMock.findById(anyLong())).thenReturn(Optional.empty());
        Assert.assertThrows(DataAccessException.class, () -> movieRatingService.setRating(Rating.LIKED, 1L, 1L));
    }

    @Test
    public void testSetRatingThrowsExceptionIfMovieIsEmpty() {
        when(movieDaoMock.findById(anyLong())).thenReturn(Optional.empty());
        Assert.assertThrows(DataAccessException.class, () -> movieRatingService.setRating(Rating.LIKED, 1L, 1L));
    }

    @Test
    public void testSetRatingChangesPreviousRatingIfExisted() {
        when(movieDaoMock.findById(anyLong())).thenReturn(Optional.of(movie));
        when(userDaoMock.findById(anyLong())).thenReturn(Optional.of(user));

        val movieRating = movieRatingService.setRating(Rating.DISLIKED, user.getId(), movie.getId());
        verify(movieRatingDaoMock, times(1)).store(movieRating);
        Assert.assertEquals(movieRating.getRating(), cz.muni.fi.pa165.entity.Rating.DISLIKED);
    }

    @Test
    public void testSetRatingAddsRatingToMovieAndUser() {
        when(movieDaoMock.findById(anyLong())).thenReturn(Optional.of(movie));
        when(userDaoMock.findById(anyLong())).thenReturn(Optional.of(user));

        val movieRating = movieRatingService.setRating(Rating.LIKED, user.getId(), movie.getId());
        Assert.assertEquals(movie.getRatings(), List.of(movieRating));
        Assert.assertEquals(user.getMovieRatings(), List.of(movieRating));
    }

    @Test
    public void testDeleteRatingThrowsExceptionWhenNoRatingByMovieAndUser() {
        Assert.assertThrows(DataAccessException.class, () -> movieRatingService.deleteRating(1L, 1L));
    }
}
