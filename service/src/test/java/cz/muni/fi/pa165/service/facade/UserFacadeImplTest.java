package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.MovieRatingDto;
import cz.muni.fi.pa165.dto.Rating;
import cz.muni.fi.pa165.dto.UserDto;
import cz.muni.fi.pa165.entity.MovieRating;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.facade.MovieRatingFacade;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.service.MovieRatingService;
import cz.muni.fi.pa165.service.UserService;
import cz.muni.fi.pa165.service.config.ServiceConfig;
import cz.muni.fi.pa165.service.converter.BeanConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.*;

/**
 * @author lsolodkova
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class UserFacadeImplTest extends AbstractTransactionalTestNGSpringContextTests {

    UserService userServiceMock = mock(UserService.class);
    UserFacade userFacade;

    @Inject
    BeanConverter beanConverter;

    private final UserDto userDto = new UserDto(1L, "user", "user@user.com");
    private User user = new User();

    @BeforeClass
    public void init(){
        User user = new User("Akira", "akira@muni.cz", "passw0rdhash");
        user.setId(1L);
    }

    @Test
    public void testFindUserById() {
    }

    @Test
    public void testFindByName() {
    }

    @Test
    public void testGetAllUsers() {
    }

    @Test
    public void testRegisterUser() {
    }

    @Test
    public void testAuthenticate() {
    }

    @Test
    public void testIsAdmin() {
    }

    @Test
    public void testRemoveUser() {
    }
}