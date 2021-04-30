package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.ActorDto;
import cz.muni.fi.pa165.entity.Actor;
import cz.muni.fi.pa165.facade.ActorFacade;
import cz.muni.fi.pa165.service.ActorService;
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

/**
 * @author alia
 */

@ContextConfiguration(classes = ServiceConfig.class)
public class ActorFacadeImplTest extends AbstractTransactionalTestNGSpringContextTests {

    @Inject
    BeanConverter converter;

    ActorService actorServiceMock = mock(ActorService.class);

    ActorFacade actorFacade;

    private final ActorDto actorDto = new ActorDto(1L, "Toshiro Mifune", 0, null, null);
    private Actor actor = new Actor();

    @BeforeClass
    public void init() {
        actor = converter.convert(actorDto, Actor.class);
        actorFacade = new ActorFacadeImpl(actorServiceMock, converter);
    }

    @BeforeMethod
    public void defineMocks() {
        when(actorServiceMock.findActorById(anyLong())).thenReturn(Optional.of(actor));
        when(actorServiceMock.findActorsByFullName(actor.getFullName())).thenReturn(List.of(actor));
    }

    @Test
    public void testCreateActor() throws ValidationException {
        Optional<ActorDto> found = actorFacade.findActorById(actorFacade.createActor(actorDto));
        Assert.assertTrue(found.isPresent());
        Assert.assertEquals(found.get(), actorDto);
    }

    @Test
    public void testDeleteActor() {
        Long actorId = actorDto.getId();
        actorFacade.deleteActor(actorId);
        verify(actorServiceMock, times(1)).deleteActor(actorId);
    }

    @Test
    public void testFindActorsByFullName() {
        Iterable<ActorDto> found = actorFacade.findActorsByFullName(actorDto.getFullName());
        Assert.assertEquals(List.of(actorDto), found);
    }
}
