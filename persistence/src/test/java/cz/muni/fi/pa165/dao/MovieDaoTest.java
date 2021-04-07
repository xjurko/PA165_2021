package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceConfig;
import cz.muni.fi.pa165.entity.Actor;
import cz.muni.fi.pa165.entity.Movie;
import lombok.val;
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
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Tests for ActorDao
 *
 * @author lsolodkova
 */
@ContextConfiguration(classes = PersistenceConfig.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class MovieDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private ActorDao actorDao;

    @Inject
    private MovieDao movieDao;

    @PersistenceContext
    private EntityManager em;

    private Actor a1;
    private Actor a2;
    private Movie m1;

    @BeforeMethod
    public void createActors() {
        a1 = new Actor();
        a1.setFullName("Vivien Leigh");
        a1.setBirthDate(LocalDate.of(1913, 11, 5));
        a1.setDeathDate(LocalDate.of(1967, 7, 8));

        a2 = new Actor();
        a2.setFullName("Bela Lugosi");
        a2.setBirthDate(LocalDate.of(1882, 10, 20));
        a2.setDeathDate(LocalDate.of(1956, 8, 16));

        m1 = new Movie();
        m1.setName("Gone with the Wind");
        movieDao.store(m1);
        a1.addMovie(m1);

        actorDao.store(a1);
        actorDao.store(a2);
    }

    @Test
    public void testDeletingMovieRemovesItFromActors() {
        val storedActor = actorDao.findById(a1.getId()).get();

        Assert.assertTrue(storedActor.getMovies().contains(m1));

        movieDao.remove(m1);
        em.flush();

        val storedActor2 = actorDao.findById(a1.getId()).get();
        Assert.assertFalse(storedActor2.getMovies().contains(m1));
        Assert.assertEquals(storedActor2.getMovies().size(), 0);
    }
}
