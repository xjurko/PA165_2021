package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.MovieDto;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.facade.MovieFacade;
import cz.muni.fi.pa165.service.MovieService;
import cz.muni.fi.pa165.service.config.ServiceConfig;
import cz.muni.fi.pa165.service.converter.BeanConverter;
import lombok.val;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author juraj
 */

//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
public class MovieFacadeImplTest extends AbstractTransactionalTestNGSpringContextTests {

    @Inject
    BeanConverter converter;

    MovieService movieServiceMock = mock(MovieService.class);

    MovieFacade movieFacade;

    private Movie testMovie = new Movie("test", Set.of(), 0, Set.of(Genre.ACTION), "test caption", "test ref");
    private MovieDto testMovieDto = new MovieDto(null, "test", "test caption", 0, Set.of(), Set.of(), Set.of());


    @BeforeClass
    public void init(){
        movieFacade = new MovieFacadeImpl(movieServiceMock, converter);
    }

    @Test
    public void testFindMovieById() {
        when(movieServiceMock.findMovieById(anyLong())).thenReturn(Optional.of(testMovie));

        Assert.assertNotNull(converter);
        val movie = movieFacade.findMovieById(123L);
        Assert.assertEquals(movie.get(), testMovieDto);
    }
}