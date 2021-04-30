package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.config.ServiceConfig;
import cz.muni.fi.pa165.service.util.TestUtil;
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
    public void whenRegisteredBlankEmailThenException() throws ValidationException {
        userService.registerUser("username", "", "passw0rd");
    }

    @Test(expectedExceptions = {ValidationException.class})
    public void whenRegisteredBlankUsernameThenException() throws ValidationException {
        userService.registerUser("", "blah@blah.com", "passw0rd");
    }

    @Test(expectedExceptions = {ValidationException.class})
    public void whenRegisteredUsernameNotMatchPatternThenException() throws ValidationException {
        userService.registerUser("01234'$'", "blah@blah.com", "passw0rd");
    }

    @Test(expectedExceptions = {ValidationException.class})
    public void whenRegisteredEmailNotMatchPatternThenException() throws ValidationException {
        userService.registerUser("newUser123", "blahblah.com", "passw0rd");
    }

    @Test(expectedExceptions = {ValidationException.class})
    public void whenRegisteredExistingUsernameThenException() throws ValidationException {
        // the reason this test was failing is because the userService.registerUser is calling mockDao so nothing is actually stored in any DB or persistence cotnext
        // when you call register user you do your duplicate validation by checking if the user already exists in DB but there is no DB to begin with
        // in orther to test that logic we need to "pretend" that there already is user with that user name in DB - we do that by mocking the DAO to return
        // some user when we use the findByNameMethod

        when(userDaoMock.findByName("username")).thenReturn(Optional.of(new User("username", "email@email.com", "passwd")));

        userService.registerUser("username", "username@blahblah.com", "12345");
    }

    @Test(expectedExceptions = {ValidationException.class})
    public void whenRegisteredExistingEmailThenException() throws ValidationException {

        when(userDaoMock.findByEmail("blah@blah.com")).thenReturn(Optional.of(new User("username", "blah@blah.com", "passwd")));

        userService.registerUser("usersameemail", "blah@blah.com", "12345");
    }

    @Test
    public void authenticateExistingCredentials(){
        User user= userService.registerUser("username", "user@email.com", "passw0rd");
        Assert.assertTrue(userService.authenticate(user, "passw0rd"));
    }

    @Test
    public void authenticateWrongPassword(){
        User user= userService.registerUser("username", "user@email.com", "passw0rd");
        Assert.assertFalse(userService.authenticate(user, "$passw0rd$"));
    }

    @Test
    public void authenticateBlankPassword() {
        User user= userService.registerUser("username", "user@email.com", "passw0rd");
        Assert.assertFalse(userService.authenticate(user, ""));
    }

    @Test
    public void checkAdminOnAdmin(){
        User admin = new User("admin", "admin@admin.com", "admin_passwdhash");
        admin.setId(1L);
        admin.setAdmin(true);
        when(userDaoMock.findById(1L)).thenReturn(Optional.of(admin));
        // not sure about this test, if it should be like that
        Assert.assertTrue(userService.isAdmin(admin));
    }
    @Test
    public void checkAdminOnNonAdmin(){
        User user  = new User("user", "user@user.com", "user_passwdhash");
        user.setId(3L);
        when(userDaoMock.findById(3L)).thenReturn(Optional.of(user));
        // not sure about this test, if it should be like that
        Assert.assertFalse(userService.isAdmin(user));
    }

}