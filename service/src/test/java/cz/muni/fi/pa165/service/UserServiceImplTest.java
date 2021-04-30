package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.config.ServiceConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import javax.validation.ValidationException;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceConfig.class)
public class UserServiceImplTest extends AbstractTransactionalTestNGSpringContextTests {

    UserDao userDaoMock = mock(UserDao.class);
    UserService userService;

    @BeforeClass
    public void init() {
        userService = new UserServiceImpl(userDaoMock);
    }

    @Test
    public void whenRegisteredNewThenSuccess() throws ValidationException {
        User u = userService.registerUser("username", "username@blahblah.com", "password");
        Assert.assertNotNull(u);
    }

    @Test(expectedExceptions = {ValidationException.class})
    public void whenRegisteredBlankPasswordThenException() throws ValidationException{
        userService.registerUser("username", "username@blahblah.com", "");
    }

    @Test(expectedExceptions = {ValidationException.class})
    public void whenRegisteredExistingUsernameThenException() throws ValidationException{
        // the reason this test was failing is because the userService.registerUser is calling mockDao so nothing is actually stored in any DB or persistence cotnext
        // when you call register user you do your duplicate validation by checking if the user already exists in DB but there is no DB to begin with
        // in orther to test that logic we need to "pretend" that there already is user with that user name in DB - we do that by mocking the DAO to return
        // some user when we use the findByNameMethod

        when(userDaoMock.findByName("username")).thenReturn(Optional.of(new User("username", "email", "hash")));

        userService.registerUser("username", "username@blahblah.com", "12345");
    }

    @Test
    public void testFindById() {
    }

    @Test
    public void testFindByName() {
    }

    @Test
    public void testGetAllUsers() {
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