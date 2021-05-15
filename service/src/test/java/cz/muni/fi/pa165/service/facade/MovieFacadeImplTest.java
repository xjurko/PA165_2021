package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.CreateMovieDto;
import cz.muni.fi.pa165.dto.MovieDto;
import cz.muni.fi.pa165.entity.Actor;
import cz.muni.fi.pa165.entity.Director;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.facade.MovieFacade;
import cz.muni.fi.pa165.service.ActorService;
import cz.muni.fi.pa165.service.DirectorService;
import cz.muni.fi.pa165.service.MovieService;
import cz.muni.fi.pa165.service.config.ServiceConfig;
import cz.muni.fi.pa165.service.converter.BeanConverter;
import lombok.val;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * @author juraj
 */

@ContextConfiguration(classes = ServiceConfig.class)
public class MovieFacadeImplTest extends AbstractTransactionalTestNGSpringContextTests {

    @Inject
    BeanConverter converter;

    MovieService movieServiceMock = mock(MovieService.class);
    DirectorService directorServiceMock = mock(DirectorService.class);
    ActorService actorsServiceMock = mock(ActorService.class);

    MovieFacade movieFacade;

    private Movie testMovie = new Movie(123L, "test", Set.of(), Set.of(), 0, Set.of(Genre.ACTION), Set.of(), "test caption", "test ref", 1990, "");
    private MovieDto testMovieDto = new MovieDto(123L, "test", "test caption", 1990, 0, Set.of(), Set.of(), "", "", List.of());
    private CreateMovieDto testCreateMovieDto = new CreateMovieDto("test", "test caption", 1990, 0, "test ref", Set.of(1L), Set.of(1L), Set.of(cz.muni.fi.pa165.dto.Genre.ACTION));
    private Director testDirector = new Director("test director").withId(1L);
    private Actor testActor = new Actor("testActor").withId(1L);
    private Movie testCreateMovie = new Movie(null, "test", Set.of(testActor), Set.of(testDirector), 0, Set.of(Genre.ACTION), Set.of(), "test caption", "test ref", 1990, "");


    @BeforeClass
    public void init() {
        movieFacade = new MovieFacadeImpl(movieServiceMock, directorServiceMock, actorsServiceMock, converter);
    }

    @BeforeMethod
    public void resetMocks() {
        reset(movieServiceMock);
        reset(directorServiceMock);
        reset(actorsServiceMock);
    }

    @Test
    public void testFindMovieById() {
        when(movieServiceMock.findMovieById(anyLong())).thenReturn(Optional.of(testMovie));

        val movie = movieFacade.getMovieById(123L);
        Assert.assertEquals(movie.get(), testMovieDto);
    }

    @Test
    public void testFindRecommendedMoviesBasedOnMovieEmpty() {
        when(movieServiceMock.findRecommendedMoviesBasedOnMovie(anyLong())).thenReturn(List.of());

        val movies = movieFacade.findRecommendedMoviesBasedOnMovie(123L);
        Assert.assertEquals(movies, List.of());
    }

    @Test
    public void testFindRecommendedMoviesBasedOnMovieNonEmpty() {
        when(movieServiceMock.findRecommendedMoviesBasedOnMovie(anyLong())).thenReturn(List.of(testMovie));

        val movies = movieFacade.findRecommendedMoviesBasedOnMovie(123L);
        Assert.assertEquals(movies, List.of(testMovieDto));
    }

    @Test
    public void testFindRecommendedMoviesForUserReturnsConvertedDtos() {
        when(movieServiceMock.findRecommendedMoviesForUser(anyLong())).thenReturn(List.of(testMovie));

        val movies = movieFacade.findRecommendedMoviesForUser(123L);
        Assert.assertEquals(movies, List.of(testMovieDto));
    }

    @Test
    public void testCreateMovieCallsUnderlyingServiceWithProperEntity() {
        when(directorServiceMock.findById(anyLong())).thenReturn(Optional.of(testDirector));
        when(actorsServiceMock.findActorById(anyLong())).thenReturn(Optional.of(testActor));

        movieFacade.createMovie(testCreateMovieDto);
        verify(movieServiceMock, times(1)).createMovie(testCreateMovie);
    }

}
