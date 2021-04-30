package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.config.ServiceConfig;
import cz.muni.fi.pa165.service.util.TestUtil;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    PasswordEncoder encoderMock;

    @BeforeClass
    public void init() {
        userDaoMock = mock(UserDao.class);
        encoderMock = mock(PasswordEncoder.class);
        when(encoderMock.encode(anyString())).thenAnswer(invocation -> invocation.getArgument(0, String.class));
        when(encoderMock.matches(anyString(), anyString())).thenAnswer(invocation -> invocation.getArgument(0, String.class).equals(invocation.getArgument(1, String.class)));
        userService = new UserServiceImpl(userDaoMock, encoderMock);
    }

    @BeforeMethod
    public void resetMock() {
        reset(userDaoMock);
    }

    @Test
    public void whenRegisteredNewThenSuccess() throws ValidationException {
        userService.registerUser("username", "username@blahblah.com", "password");
        verify(userDaoMock, times(1)).store(new User("username", "username@blahblah.com", encoderMock.encode("password")));

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

        when(userDaoMock.findByName("username")).thenReturn(Optional.of(new User("username", "email@email.com", "passwd")));

        userService.registerUser("username", "username@blahblah.com", "12345");
    }

    @Test(expectedExceptions = {ValidationException.class})
    public void whenRegisteredExistingEmailThenException() throws ValidationException {

        when(userDaoMock.findByEmail("blah@blah.com")).thenReturn(Optional.of(new User("username", "blah@blah.com", "passwd")));

        userService.registerUser("usersameemail", "blah@blah.com", "12345");
    }

    @Test
    public void authenticateExistingCredentials() {
        User user = userService.registerUser("username", "user@email.com", "passw0rd");
        Assert.assertTrue(userService.authenticate(user, "passw0rd"));
    }

    @Test
    public void authenticateWrongPassword() {
        User user = userService.registerUser("username", "user@email.com", "passw0rd");
        Assert.assertFalse(userService.authenticate(user, "$passw0rd$"));
    }

    @Test
    public void authenticateBlankPassword() {
        User user = userService.registerUser("username", "user@email.com", "passw0rd");
        Assert.assertFalse(userService.authenticate(user, ""));
    }

    @Test
    public void checkAdminOnAdmin() {
        User admin = new User("admin", "admin@admin.com", "admin_passwdhash");
        admin.setId(1L);
        admin.setAdmin(true);
        when(userDaoMock.findById(1L)).thenReturn(Optional.of(admin));
        Assert.assertTrue(userService.isAdmin(admin));
    }
    @Test
    public void checkAdminOnNonAdmin() {
        User user = new User("user", "user@user.com", "user_passwdhash");
        user.setId(3L);
        when(userDaoMock.findById(3L)).thenReturn(Optional.of(user));
        Assert.assertFalse(userService.isAdmin(user));
    }

    @Test
    public void checkAdminOnNonExisting() {
        when(userDaoMock.findById(anyLong())).thenReturn(Optional.empty());
        Assert.assertFalse(userService.isAdmin(new User("user", "user@email.com", "passw0rdhash")));
    }

    @Test
    public void whenRemoveNonExistingUserThenException(){
        when(userDaoMock.findById(anyLong())).thenReturn(Optional.empty());
        Assert.assertThrows(DataRetrievalFailureException.class, () -> userService.removeUser(TestUtil.getFakeUser(1L, "user")));
    }

}