package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceConfig;
import cz.muni.fi.pa165.entity.Actor;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

/**
 * Tests for ActorDao
 *
 * @author lsolodkova
 */
@ContextConfiguration(classes = PersistenceConfig.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ActorDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ActorDao actorDao;

    @Inject
    private MovieDao movieDao;

    @PersistenceContext
    private EntityManager em;

    private Actor a1;
    private Actor a2;

    @BeforeMethod
    public void createActors(){
        a1 = new Actor();
        a1.setFullName("Vivien Leigh");
        a1.setBirthDate(LocalDate.of(1913,11,5));
        a1.setDeathDate(LocalDate.of(1967, 7, 8));

        a2 = new Actor();
        a2.setFullName("Bela Lugosi");
        a2.setBirthDate(LocalDate.of(1882, 10, 20));
        a2.setDeathDate(LocalDate.of(1956, 8, 16));

        /*TODO: add movies after getters/setters in Movie class implemented*/

        actorDao.store(a1);
        actorDao.store(a2);
    }

    @Test
    public void testFetchAll() {
        List<Actor> found = actorDao.fetchAll();
        Assert.assertTrue(found.containsAll(List.of(a1, a2)));
    }


}
