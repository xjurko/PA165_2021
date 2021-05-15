package cz.muni.fi.pa165.service.facade;

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

import javax.inject.Inject;
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

    private final DirectorDto directorDto = new DirectorDto(1L, "Quentin Tarantino", null, null, "");
    private final Director director = new Director("Quentin Tarantino");

    @BeforeClass
    public void init() {
        directorFacade = new DirectorFacadeImpl(directorServiceMock, converter);
    }

    @BeforeMethod
    public void defineMocks() {
        when(directorServiceMock.findById(anyLong())).thenReturn(Optional.of(director));
        when(directorServiceMock.findByName(director.getName())).thenReturn(List.of(director.withId(1L)));
    }

    @Test
    public void testCreateDirector() throws ValidationException {
        directorFacade.createDirector(directorDto);
        verify(directorServiceMock, times(1)).createDirector(director);
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
