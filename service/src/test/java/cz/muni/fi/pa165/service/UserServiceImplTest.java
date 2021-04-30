package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.config.ServiceConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ValidationException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


/**
 * @author lsolodkova
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class UserServiceImplTest extends AbstractTransactionalTestNGSpringContextTests {

    UserDao userDaoMock;
    UserService userService;
    BCryptPasswordEncoder encoder;

    @BeforeClass
    public void init() {
        userDaoMock = mock(UserDao.class);
        userService = new UserServiceImpl(userDaoMock);
        encoder = new BCryptPasswordEncoder();
    }

    @BeforeMethod
    public void resetMock() {
        // without this tests are flaky - we need to make sure we reset mock behavior between tests as otherwise the mocked behavior in one test persists in other tests
        // this is expecially important since we are mocking the same method with different behaviours in different tests

        reset(userDaoMock);
    }

    @Test
    public void whenRegisteredNewThenSuccess() throws ValidationException {
        // this is actually not needed mockito returns Optional.empty by defualt for methods that reutrn Optionals  so feel free to remove either this line of the reset before method
//        important thing to realize here is that without either of these mock resets the test are flaky due to teh fact that the order in which they are run is not guaranteed

        User u = userService.registerUser("username", "username@blahblah.com", "password");
        verify(userDaoMock, times(1)).store(new User("username", "username@blahblah.com", encoder.encode("password")));

    }

    @Test(expectedExceptions = {ValidationException.class})
    public void whenRegisteredBlankPasswordThenException() throws ValidationException {
        userService.registerUser("username", "username@blahblah.com", "");
    }

    @Test(expectedExceptions = {ValidationException.class})
    public void whenRegisteredExistingUsernameThenException() throws ValidationException {
        // the reason this test was failing is because the userService.registerUser is calling mockDao so nothing is actually stored in any DB or persistence cotnext
        // when you call register user you do your duplicate validation by checking if the user already exists in DB but there is no DB to begin with
        // in orther to test that logic we need to "pretend" that there already is user with that user name in DB - we do that by mocking the DAO to return
        // some user when we use the findByNameMethod

        when(userDaoMock.findByName("username")).thenReturn(Optional.of(new User("username", "email", "hash")));

        userService.registerUser("username", "username@blahblah.com", "12345");
    }


}