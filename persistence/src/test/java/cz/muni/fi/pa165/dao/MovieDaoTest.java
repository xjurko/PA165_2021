package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceConfig;
import cz.muni.fi.pa165.entity.*;
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
    private ActorDao actorDao;

    @Inject
    private MovieDao movieDao;

    @PersistenceContext
    private EntityManager em;


    private Movie m1;
    private Movie m2;
    private Movie m3;
    private Movie m4;

    private Actor a1;
    private Actor a2;

    @BeforeMethod
    public void createMovies() {
        a1 = new Actor();
        a1.setFullName("Vivien Leigh");
        a1.setBirthYear(1913);
        a1.setDeathYear(1967);

        a2 = new Actor();
        a2.setFullName("Bela Lugosi");
        a2.setBirthYear(1882);
        a2.setDeathYear(1956);

        actorDao.store(a1);
        actorDao.store(a2);

        Set<Director> directors = new HashSet<>();
        directors.add(new Director("director1"));

        Set<Genre> genres = new HashSet<>();
        genres.add(Genre.COMEDY);

        m1 = new Movie("TEst1", 100, 1999, "amazing movie",
            "external reference").withDirectors(directors).withGenres(genres);
        m2 = new Movie("test2", 100, 1999, "amazing movie",
            "external reference").withDirectors(directors).withGenres(genres);

        m3 = new Movie("123test11", 100, 1999, "amazing movie",
            "external reference").withDirectors(directors).withGenres(genres);

        m4 = new Movie("not match", 100, 1999, "amazing movie",
            "external reference").withDirectors(directors).withGenres(genres);

        m1.addCastMember(a1);
        movieDao.store(m1);
        movieDao.store(m2);
        movieDao.store(m3);
        movieDao.store(m4);
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
    public void testAddingActorAlsoAddsMovieToThatActor() {
        val storedActor = actorDao.findById(a1.getId()).get();

        Assert.assertTrue(storedActor.getMovies().contains(m1));
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
        Assert.assertEquals(Set.copyOf(movieDao.fetchAll()), Set.of(m2, m3, m4));

        movieDao.remove(m2);
        em.flush();
        Assert.assertTrue(movieDao.findById(m2.getId()).isEmpty());
        Assert.assertEquals(Set.copyOf(movieDao.fetchAll()), Set.of(m3, m4));
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


    @Test
    public void testFindMovieBySubstringCaseInsensitive() {
        val storedMovies1 = movieDao.findByName("test");
        val storedMovies2 = movieDao.findByName("EsT");

        Assert.assertEquals(Set.copyOf(storedMovies1), Set.of(m1, m2, m3));
        Assert.assertEquals(Set.copyOf(storedMovies2), Set.of(m1, m2, m3));
    }
}

