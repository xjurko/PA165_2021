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
import java.util.Set;
import java.util.Optional;

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
    public void fetchAll() {
        List<Actor> found = actorDao.fetchAll();
        Assert.assertTrue(found.containsAll(List.of(a1, a2)));
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullNameNotAllowed() {
        Actor a = new Actor();
        actorDao.store(a);
    }

    @Test
    public void findById() {
        Optional<Actor> actor = actorDao.findById(a1.getId());
        Assert.assertTrue(actor.isPresent());
        Assert.assertEquals(actor, Optional.of(a1));
    }

    @Test
    public void findByName() {
        Assert.assertEquals(actorDao.findByFullName("Bela Lugosi").size(), 1);
        Assert.assertEquals(actorDao.findByFullName("ghjdhgj").size(), 0);
    }

    @Test
    public void findMovie() {
        List<Actor> actors = actorDao.findByFullName("Vivien Leigh");
        Assert.assertEquals(actors.size(), 1);
        Assert.assertEquals(actors.get(0).getMovies().size(), 1);
        Assert.assertEquals(actors.get(0).getMovies().iterator().next().getName(), "Gone with the Wind");
    }

    @Test
    public void remove() {
        Assert.assertFalse(actorDao.findByFullName(a2.getFullName()).isEmpty());
        actorDao.remove(a2);
        Assert.assertTrue(actorDao.findByFullName(a2.getFullName()).isEmpty());
    }

    @Test
    public void testAddingMovieAlsoAddsActorToThatMoviesCast() {
        val storedMovie = movieDao.findById(m1.getId()).get();

        Assert.assertTrue(storedMovie.getCast().contains(a1));
    }


    @Test
    public void testDeletingActorRemovesItFromMovies() {
        val movie = new Movie("testMovie", Set.of(), 10, Set.of(), "", "");
        movie.addCastMember(a1);
        movie.addCastMember(a2);
        movieDao.store(movie);
        em.flush();

        actorDao.remove(a1);
        em.flush();

        val storedMovie = movieDao.findById(movie.getId()).get();
        Assert.assertTrue(storedMovie.getCast().contains(a2));
        Assert.assertFalse(storedMovie.getCast().contains(a1));
        Assert.assertEquals(storedMovie.getCast().size(), 1);
    }
}
