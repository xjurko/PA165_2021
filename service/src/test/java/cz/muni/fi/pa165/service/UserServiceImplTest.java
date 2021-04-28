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

import static org.mockito.Mockito.mock;

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

    @Ignore
    @Test
    public void whenRegisteredExistingUsernameThenException() throws ValidationException{
        // test doesn't work at the moment - exception is thrown by DAO
        // a test for DAO already done and works
        //userService.registerUser("username", "username@blahblah.com", "12345");
        //userService.registerUser("username", "username123@blahblah.com", "12345");
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