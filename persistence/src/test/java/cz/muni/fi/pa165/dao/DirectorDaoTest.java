package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceConfig;
import cz.muni.fi.pa165.entity.Director;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Tests for DirectorDao
 *
 * @author alia
 */

@Ignore("enable when director implemented")
@ContextConfiguration(classes = PersistenceConfig.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class DirectorDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DirectorDao directorDao;

    @Test(expectedExceptions= ConstraintViolationException.class)
    public void testNullNameNotAllowed() {
        Director d1 = new Director(null);
        directorDao.store(d1);
    }

    @Test
    public void testFetchAll() {
        Director d1 = new Director("d1");
        Director d2 = new Director("d2");

        directorDao.store(d1);
        directorDao.store(d2);

        List<Director> directors  = directorDao.fetchAll();
        assertEquals(directors.size(), 2);
        assertTrue(directors.containsAll(List.of(d1, d2)));
    }

    @Test
    public void testFindById() {
        Director d1 = new Director("d1");
        directorDao.store(d1);

        Optional<Director> found = directorDao.findById(d1.getId());

        assertNotNull(found);
        assertEquals(Optional.of(d1), found);
    }

    @Test
    public void testFindByName() {
        Director d1 = new Director("d1");
        directorDao.store(d1);

        Optional<Director> found = directorDao.findByName(d1.getName());

        assertNotNull(found);
        assertEquals(Optional.of(d1), found);
    }

    @Test
    public void testRemove() {
        Director d1 = new Director("d1");
        directorDao.store(d1);
        assertNotNull(directorDao.findById(d1.getId()));
        directorDao.remove(d1);
        assertNull(directorDao.findById(d1.getId()));
    }
}
