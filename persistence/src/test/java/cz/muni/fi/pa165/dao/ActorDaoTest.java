package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceConfig;
import cz.muni.fi.pa165.entity.Actor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
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

    @Inject
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

        /*TODO: add movies and proper tests after getters/setters in Movie class implemented*/

        actorDao.store(a1);
        actorDao.store(a2);
    }

    @Test
    public void fetchAll() {
        List<Actor> found = actorDao.fetchAll();
        Assert.assertTrue(found.containsAll(List.of(a1, a2)));
    }

    @Test(expectedExceptions= ConstraintViolationException.class)
    public void nullNameNotAllowed(){
        Actor a = new Actor();
        actorDao.store(a);
    }

    /*TODO: test for a blank name (not null, just empty or of 77 space characters)
    * after proper constraints in Actor class implemented
    */

    @Test(expectedExceptions = ConstraintViolationException.class)
    @Ignore("enable when Actor name will be constrained to be non-blank")
    public void zeroLengthNameNotAllowed(){
        Actor a = new Actor();
        a.setFullName("");
        actorDao.store(a);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    @Ignore("enable when Actor name will be constrained to be non-blank")
    public void blankNameNotAllowed(){
        Actor a = new Actor();
        /*TODO: how to properly test for all-whitespace string?*/
        a.setFullName("             ");
        actorDao.store(a);
    }

    /*TODO: a test for getById when id getter implemented in Actor (if needed)*/

    @Test
    public void findByName()
    {
        Assert.assertEquals(actorDao.findByFullName("Bela Lugosi").size(), 1);
        Assert.assertEquals(actorDao.findByFullName("ghjdhgj").size(), 0);
        /*TODO: also test for partial matching when implemented*/
    }

    @Test
    public void remove()
    {
        /*TODO: Optional return type is not used in ActorDao; possibly needs to be changed*/
        Assert.assertFalse(actorDao.findByFullName(a2.getFullName()).isEmpty());
        actorDao.remove(a2);
        Assert.assertTrue(actorDao.findByFullName(a2.getFullName()).isEmpty());
    }
}
