package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.ActorDao;
import cz.muni.fi.pa165.entity.Actor;
import cz.muni.fi.pa165.exceptions.ValidationException;
import cz.muni.fi.pa165.service.config.ServiceConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author alia
 */

@ContextConfiguration(classes = ServiceConfig.class)
public class ActorServiceImplTest extends AbstractTransactionalTestNGSpringContextTests {

    ActorDao actorDaoMock = mock(ActorDao.class);
    ActorService actorService;

    private Actor a = new Actor();

    @BeforeClass
    public void init() {
        actorService = new ActorServiceImpl(actorDaoMock);
    }

    @BeforeMethod
    public void setFields() {
        a = new Actor("Toshiro Mifune", null, null, null);
        a.setId(1L);
    }

    @Test
    public void testCreateActorThrowsExceptionWhenFullNameIsNull(){
        a.setFullName(null);
        Assert.assertThrows(ValidationException.class, () -> actorService.createActor(a));
    }

    @Test
    public void testCreateActorThrowsExceptionWhenFullNameIsEmpty(){
        a.setFullName("");
        Assert.assertThrows(ValidationException.class, () -> actorService.createActor(a));
    }

    @Test
    public void testCreateActorThrowsExceptionWhenOnlySpacesFullName(){
        a.setFullName("   ");
        Assert.assertThrows(ValidationException.class, () -> actorService.createActor(a));
    }

    @Test
    public void testCreateActorThrowsExceptionWhenNegativeHeight() {
        a.setHeight(-5.0);
        Assert.assertThrows(ValidationException.class, () -> actorService.createActor(a));
    }

    @Test
    public void testCreateActorThrowsExceptionWhenDeathBeforeBirth() {
        a.setBirthDate(LocalDate.of(2000, 1, 1));
        a.setDeathDate(LocalDate.of(1999, 1, 1));
        Assert.assertThrows(ValidationException.class, () -> actorService.createActor(a));
    }

    @Test
    public void testCreateActorTrimsFullNameProperly() {
        a.setFullName("    Toshiro Mifune   ");

        when(actorDaoMock.findById(a.getId())).thenReturn(Optional.of(a));

        Long foundId = 100L;
        try {
            foundId = actorService.createActor(a);
        } catch (ValidationException e) {
            Assert.fail("No exception should be thrown" + e.getMessage());
        }

        Optional<Actor> found = actorService.findActorById(foundId);
        Assert.assertTrue(found.isPresent());
        Assert.assertEquals(found.get().getFullName(), "Toshiro Mifune");
    }

    @Test
    public void testDeleteActorThrowsExceptionWhenNoActorIdInDatabase() {
        when(actorDaoMock.findById(1L)).thenReturn(Optional.empty());

        Assert.assertThrows(ValidationException.class, () -> actorService.deleteActor(1L));
    }
}
