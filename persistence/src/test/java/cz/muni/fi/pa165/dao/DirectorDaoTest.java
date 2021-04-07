package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceConfig;
import cz.muni.fi.pa165.entity.Director;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Tests for DirectorDao
 *
 * @author alia
 */

@ContextConfiguration(classes = PersistenceConfig.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class DirectorDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    EntityManager em;

    @Inject
    private DirectorDao directorDao;

    @Test(expectedExceptions= ConstraintViolationException.class)
    public void testNullNameNotAllowed() {
        Director d1 = new Director(null);
        directorDao.store(d1);
    }

    /*TODO: add more tests */

    @Test
    public void testFetchAll() {
        Director d1 = new Director("Richard Linklater");
        Director d2 = new Director("Akira Kurosawa");

        directorDao.store(d1);
        directorDao.store(d2);

        List<Director> directors  = directorDao.fetchAll();
        assertEquals(directors.size(), 2);
        assertTrue(directors.containsAll(List.of(d1, d2)));
    }

    @Test
    public void testFindById() {
        Director d1 = new Director("Zdenek Sverak");
        directorDao.store(d1);

        Optional<Director> found = directorDao.findById(d1.getId());

        assertTrue(found.isPresent());
        assertEquals(Optional.of(d1), found);

        assertTrue(directorDao.findById(d1.getId() + 1).isEmpty());
    }

    @Test
    public void testFindByName() {
        Director d1 = new Director("Zdenek Sverak");
        directorDao.store(d1);

        Optional<Director> found = directorDao.findByName(d1.getName());

        assertTrue(found.isPresent());
        assertEquals(Optional.of(d1), found);

        assertTrue(directorDao.findByName("Akira Kurosawa").isEmpty());
    }

    @Test
    public void testRemove() {
        Director d1 = new Director("Richard Linklater");
        Director d2 = new Director("Akira Kurosawa");

        directorDao.store(d1);
        directorDao.store(d2);

        em.flush();

        assertTrue(directorDao.findById(d1.getId()).isPresent());
        assertTrue(directorDao.findById(d2.getId()).isPresent());

        directorDao.remove(d1);
        em.flush();
        assertTrue(directorDao.findById(d1.getId()).isEmpty());
        assertTrue(directorDao.findById(d2.getId()).isPresent());
        assertEquals(directorDao.fetchAll().size(), 1);

        directorDao.remove(d2);
        em.flush();
        assertTrue(directorDao.findById(d2.getId()).isEmpty());
        assertEquals(directorDao.fetchAll().size(), 0);
    }
}
