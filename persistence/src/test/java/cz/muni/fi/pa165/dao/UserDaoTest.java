package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceConfig;
import cz.muni.fi.pa165.entity.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.*;

@ContextConfiguration(classes = PersistenceConfig.class)
public class UserDaoTest {
    @Inject
    private UserDao userDao;

//    @PersistenceContext
//    private EntityManager em;

    @Test
    void testStoreAndFind(User m) {
        User user = new User("testUser1", "user1@fi.muni.cz");
        userDao.store(user);

        Optional<User> storedUser = userDao.findById(user.getId());
        Assert.assertTrue(storedUser.isPresent());
        Assert.assertEquals(storedUser, Optional.of(user));
    }

    @Test
    void testStoreAndFindAll() {
        User user1 = new User("testUser1", "user1@fi.muni.cz");
        User user2 = new User("testUser2", "user2@fi.muni.cz");

        userDao.store(user1);
        userDao.store(user2);

        List<User> storedUsers = userDao.fetchAll();
        Assert.assertTrue(storedUsers.containsAll(List.of(user1, user2)));
    }

    @Test
    void testStoreAndRemove() {
        User user = new User("testUser1", "user1@fi.muni.cz");

        userDao.store(user);
        Assert.assertTrue(userDao.findById(user.getId()).isPresent());

        userDao.remove(user.getId());
        Assert.assertTrue(userDao.findById(user.getId()).isEmpty());
    }

}
