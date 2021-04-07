package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceConfig;
import cz.muni.fi.pa165.entity.*;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Tests for MovieDao
 *
 * @author Richard Sanda
 */

@ContextConfiguration(classes = PersistenceConfig.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class MovieDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private MovieDao movieDao;

    @PersistenceContext
    private EntityManager em;

    private Movie m1;
    private Movie m2;

    @BeforeMethod
    public void createMovies(){
        Set<Actor> actors = new HashSet<>();
        actors.add(new Actor("actor1"));

        Set<Director> directors = new HashSet<>();
        directors.add(new Director("director1"));

        Set<Genre> genres = new HashSet<>();
        genres.add(Genre.COMEDY);

        m1 = new Movie("test1", actors, directors, 100, genres, "amazing movie",
                "external reference");
        m2 = new Movie("test2", actors, directors, 100, genres, "amazing movie",
                "external reference");

        movieDao.store(m1);
        movieDao.store(m2);
    }

    @Test
    public void testStoreAndFindById() {
        Optional<Movie> storedMovie = movieDao.findById(m1.getId());
        Assert.assertTrue(storedMovie.isPresent());
        Assert.assertEquals(storedMovie, Optional.of(m1));
    }

    @Test
    public void testStoreAndFindAll() {
        List<Movie> storedMovie = movieDao.fetchAll();
        Assert.assertTrue(storedMovie.containsAll(List.of(m1, m2)));
    }

    @Test
    public void testRemove() {
        em.flush();

        Assert.assertTrue(movieDao.findById(m1.getId()).isPresent());
        Assert.assertTrue(movieDao.findById(m2.getId()).isPresent());

        movieDao.remove(m1);
        em.flush();
        Assert.assertTrue(movieDao.findById(m1.getId()).isEmpty());
        Assert.assertTrue(movieDao.findById(m2.getId()).isPresent());
        Assert.assertEquals(movieDao.fetchAll().size(), 1);

        movieDao.remove(m2);
        em.flush();
        Assert.assertTrue(movieDao.findById(m2.getId()).isEmpty());
        Assert.assertEquals(movieDao.fetchAll().size(), 0);
    }

}