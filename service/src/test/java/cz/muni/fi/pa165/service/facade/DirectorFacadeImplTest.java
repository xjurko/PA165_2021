package cz.muni.fi.pa165.service.facade;

import com.github.dozermapper.core.inject.Inject;
import cz.muni.fi.pa165.dto.DirectorDto;
import cz.muni.fi.pa165.entity.Director;
import cz.muni.fi.pa165.facade.DirectorFacade;
import cz.muni.fi.pa165.service.DirectorService;
import cz.muni.fi.pa165.service.config.ServiceConfig;
import cz.muni.fi.pa165.service.converter.BeanConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

/**
 * @author Richard Sanda
 */

@ContextConfiguration(classes = ServiceConfig.class)
public class DirectorFacadeImplTest extends AbstractTransactionalTestNGSpringContextTests {

    @Inject
    BeanConverter converter;

    DirectorService directorServiceMock = mock(DirectorService.class);

    DirectorFacade directorFacade;

    private final DirectorDto directorDto = new DirectorDto(1L, "Quentin Tarantino", null);
    private Director director = new Director();

    @BeforeClass
    public void init() {
        director = converter.convert(directorDto, Director.class);
        directorFacade = new DirectorFacadeImpl(directorServiceMock, converter);
    }

    @BeforeMethod
    public void defineMocks() {
        when(directorServiceMock.findById(anyLong())).thenReturn(Optional.of(director));
        when(directorServiceMock.findByName(director.getName())).thenReturn(List.of(director));
    }

    @Test
    public void testCreateDirector() throws ValidationException {
        Optional<DirectorDto> found = directorFacade.findById(directorFacade.createDirector(directorDto));
        Assert.assertTrue(found.isPresent());
        Assert.assertEquals(found.get(), directorDto);
    }

    @Test
    public void testDeleteDirector() {
        Long directorId = directorDto.getId();
        directorFacade.deleteDirector(directorId);
        verify(directorServiceMock, times(1)).deleteDirector(directorId);
    }

    @Test
    public void testFindDirectorsByName() {
        Iterable<DirectorDto> found = directorFacade.findByName(directorDto.getName());
        Assert.assertEquals(List.of(directorDto), found);
    }
}
