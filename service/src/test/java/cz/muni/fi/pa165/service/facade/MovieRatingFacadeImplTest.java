package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.MovieRatingDto;
import cz.muni.fi.pa165.dto.Rating;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.MovieRating;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.facade.MovieRatingFacade;
import cz.muni.fi.pa165.service.MovieRatingService;
import cz.muni.fi.pa165.service.config.ServiceConfig;
import cz.muni.fi.pa165.service.converter.BeanConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * @author alia
 */

@ContextConfiguration(classes = ServiceConfig.class)
public class MovieRatingFacadeImplTest extends AbstractTransactionalTestNGSpringContextTests {

    MovieRatingService movieRatingServiceMock = mock(MovieRatingService.class);

    MovieRatingFacade movieRatingFacade;

    @Inject
    BeanConverter beanConverter;

    private final MovieRatingDto movieRatingDto = new MovieRatingDto(1L, 1L, Rating.LIKED);
    private MovieRating movieRating = new MovieRating();

    @BeforeClass
    public void init() {
        Movie movie = new Movie();
        movie.setName("Seven Samurai");
        movie.setId(1L);
        User user = new User("Akira", "akira@muni.cz", "passw0rdhash");
        user.setId(1L);
        movieRating = new MovieRating(movie, user, cz.muni.fi.pa165.entity.Rating.LIKED);
        movieRatingFacade = new MovieRatingFacadeImpl(movieRatingServiceMock,  beanConverter);
    }

    @Test
    public void testSetRating() {
        when(movieRatingServiceMock.findRatingByUserAndMovie(1L, 1L)).thenReturn(Optional.of(movieRating));
        movieRatingFacade.setRating(new MovieRatingDto( 1L, 1L, Rating.LIKED));
        verify(movieRatingServiceMock, times(1)).setRating(cz.muni.fi.pa165.entity.Rating.LIKED, 1L, 1L);
    }

    @Test
    public void testFindRatingsByMovie() {
        when(movieRatingServiceMock.findRatingsForMovie(1L)).thenReturn(List.of(movieRating));
        Assert.assertEquals(List.of(movieRatingDto), movieRatingFacade.findRatingsByMovie(1L));
    }

    @Test
    public void testFindRatingsByUser() {
        when(movieRatingServiceMock.findRatingsByUser(1L)).thenReturn(List.of(movieRating));
        Assert.assertEquals(List.of(movieRatingDto), movieRatingFacade.findRatingsByUser(1L));
    }

    @Test
    public void testDeleteRating() {
        movieRatingFacade.deleteRating(1L, 1L);
        verify(movieRatingServiceMock, times(1)).deleteRating(1L, 1L);
    }

}
