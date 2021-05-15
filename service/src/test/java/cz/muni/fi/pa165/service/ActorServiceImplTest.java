package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.ActorDao;
import cz.muni.fi.pa165.entity.Actor;
import cz.muni.fi.pa165.service.config.ServiceConfig;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

/**
 * @author alia
 */

@ContextConfiguration(classes = ServiceConfig.class)
public class ActorServiceImplTest extends AbstractTransactionalTestNGSpringContextTests {

    ActorDao actorDaoMock = mock(ActorDao.class);
    ActorService actorService;

    private Actor actor = new Actor();

    @BeforeClass
    public void init() {
        actorService = new ActorServiceImpl(actorDaoMock);
    }

    @BeforeMethod
    public void setFields() {
        actor = new Actor("Toshiro Mifune", null, null);
        actor.setId(1L);
    }

    @Test
    public void testCreateActorThrowsExceptionWhenFullNameIsNull(){
        actor.setFullName(null);
        Assert.assertThrows(ValidationException.class, () -> actorService.createActor(actor));
    }

    @Test
    public void testCreateActorThrowsExceptionWhenFullNameIsEmpty(){
        actor.setFullName("");
        Assert.assertThrows(ValidationException.class, () -> actorService.createActor(actor));
    }

    @Test
    public void testCreateActorThrowsExceptionWhenOnlySpacesFullName(){
        actor.setFullName("   ");
        Assert.assertThrows(ValidationException.class, () -> actorService.createActor(actor));
    }

    @Test
    public void testCreateActorThrowsExceptionWhenDeathBeforeBirth() {
        actor.setBirthDate(2000);
        actor.setDeathDate(1999);
        Assert.assertThrows(ValidationException.class, () -> actorService.createActor(actor));
    }

    @Test
    public void testCreateActorTrimsFullNameProperly() throws ValidationException {
        actor.setFullName("    Toshiro Mifune   ");
        actorService.createActor(actor);
        Assert.assertEquals(actor.getFullName(), "Toshiro Mifune");
        verify(actorDaoMock, times(1)).store(actor);
    }

    @Test
    public void testDeleteActorThrowsExceptionWhenNoActorIdInDatabase() {
        when(actorDaoMock.findById(anyLong())).thenReturn(Optional.empty());
        Assert.assertThrows(DataAccessException.class, () -> actorService.deleteActor(1L));
    }
}
