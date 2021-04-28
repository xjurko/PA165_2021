package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceConfig;
import cz.muni.fi.pa165.entity.Director;
import cz.muni.fi.pa165.entity.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.testng.AssertJUnit.assertTrue;


/**
 * Tests for UserDao
 *
 * @author juraj
 */

@ContextConfiguration(classes = PersistenceConfig.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private UserDao userDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testStoreAndFind() {
        User user = new User("testUser1", "user1@fi.muni.cz", "passw0rdhash");
        userDao.store(user);

        Optional<User> storedUser = userDao.findById(user.getId());
        Assert.assertTrue(storedUser.isPresent());
        Assert.assertEquals(storedUser, Optional.of(user));
    }

    @Test
    public void testStoreAndFindAll() {
        User user1 = new User("testUser1", "user1@fi.muni.cz", "passw0rdhash");
        User user2 = new User("testUser2", "user2@fi.muni.cz", "passw0rdhash");

        userDao.store(user1);
        userDao.store(user2);

        List<User> storedUsers = userDao.fetchAll();
        Assert.assertTrue(storedUsers.containsAll(List.of(user1, user2)));
    }

    @Test
    public void testStoreAndRemove() {
        User user = new User("testUser1", "user1@fi.muni.cz", "passw0rdhash");

        userDao.store(user);
        Assert.assertTrue(userDao.findById(user.getId()).isPresent());

        userDao.remove(user);
        Assert.assertTrue(userDao.findById(user.getId()).isEmpty());
    }

    @Test(expectedExceptions = {ConstraintViolationException.class})
    public void testSaveUsersSameName(){
        User user1 = new User("samename", "user1@fi.muni.cz", "passw0rdhash");
        User user2 = new User("samename", "user2@fi.muni.cz", "passw0rdhash");

        userDao.store(user1);
        userDao.store(user2);
    }

}
