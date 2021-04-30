package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.DirectorDao;
import cz.muni.fi.pa165.entity.Director;
import cz.muni.fi.pa165.service.DirectorService;
import cz.muni.fi.pa165.service.DirectorServiceImpl;
import cz.muni.fi.pa165.service.config.ServiceConfig;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ValidationException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

/**
 * @author Richard Sanda
 */

@ContextConfiguration(classes = ServiceConfig.class)
public class DirectorServiceImplTest extends AbstractTransactionalTestNGSpringContextTests {
    DirectorDao directorDaoMock = mock(DirectorDao.class);
    DirectorService directorService;

    private Director director = new Director();

    @BeforeClass
    public void init() {
        directorService = new DirectorServiceImpl(directorDaoMock);
    }

    @BeforeMethod
    public void setFields() {
        director = new Director("Quentin Tarantino", null);
        director.setId(1L);
    }

    @Test
    public void testCreateDirectorWithNullNameException(){
        director.setName(null);
        Assert.assertThrows(ValidationException.class, () -> directorService.createDirector(director));
    }

    @Test
    public void testCreateDirectorWithEmptyNameException(){
        director.setName("");
        Assert.assertThrows(ValidationException.class, () -> directorService.createDirector(director));
    }

    @Test
    public void testCreateDirectorWithEmptyNameOnlyItsWithBlankSpacesException(){
        director.setName("      ");
        Assert.assertThrows(ValidationException.class, () -> directorService.createDirector(director));
    }

    @Test
    public void testCreateDirectorAndTestTrimmedName() throws ValidationException {
        director.setName("    Quentin Tarantino          ");
        directorService.createDirector(director);
        Assert.assertEquals(director.getName(), "Quentin Tarantino");
        verify(directorDaoMock, times(1)).store(director);
    }

    @Test
    public void testDeleteDirectorThrowsExceptionWhenNoDirectorIdInDatabase() {
        when(directorDaoMock.findById(anyLong())).thenReturn(Optional.empty());
        Assert.assertThrows(DataAccessException.class, () -> directorService.deleteDirector(1L));
    }
}
