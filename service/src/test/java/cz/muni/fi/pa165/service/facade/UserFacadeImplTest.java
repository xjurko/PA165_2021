package cz.muni.fi.pa165.service.facade;


import cz.muni.fi.pa165.dto.UserDto;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.service.UserService;
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

import static org.mockito.Mockito.*;

/**
 * @author lsolodkova
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class UserFacadeImplTest extends AbstractTransactionalTestNGSpringContextTests {

    private final UserDto userDto = new UserDto(1L, "user", "user@user.com", "", false);
    UserService userServiceMock;
    UserFacade userFacade;
    @Inject
    BeanConverter converter;

    private User user = new User("user", "user@user.com", "");

    @BeforeClass
    public void init() {
        userServiceMock = mock(UserService.class);
        userFacade = new UserFacadeImpl(userServiceMock, converter);
    }

    @Test
    public void testFindUserById() {
        when(userServiceMock.findById(1L)).thenReturn(Optional.of(user.withId(1L)));
        Assert.assertEquals(userDto, userFacade.findUserById(1L).get());
    }

    @Test
    public void testFindByName() {
        when(userServiceMock.findByName("user")).thenReturn(Optional.of(user.withId(1L)));
        Assert.assertEquals(userDto, userFacade.findByName("user").get());
    }

    @Test
    public void testRegisterUser() {
        when(userServiceMock.registerUser("user", "user@user.com", "password")).thenReturn(user);
        Assert.assertEquals(userFacade.registerUser("user", "user@user.com", "password"), user.getId());
    }

    @Test
    public void testIsAdmin() {
        when(userServiceMock.isAdmin(any(User.class))).thenReturn(true);
        val isAdmin = userFacade.isAdmin(userDto);
        verify(userServiceMock, times(1)).isAdmin(user.withId(1L));
        Assert.assertTrue(isAdmin);
    }

    @Test
    public void testRemoveUser() {
        userFacade.removeUser(1L);
        verify(userServiceMock, times(1)).removeUser(1L);
    }
}