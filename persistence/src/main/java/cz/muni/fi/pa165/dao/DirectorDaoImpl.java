package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Actor;
import cz.muni.fi.pa165.entity.Director;
import cz.muni.fi.pa165.entity.Movie;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Director DAO implementation
 *
 * @author Richard Sanda
 */

@Repository
public class DirectorDaoImpl implements DirectorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void store(Director d) {
        em.persist(d);
    }

    @Override
    public List<Director> fetchAll() {
        return em.createQuery("select d from Director d", Director.class)
                .getResultList();
    }

    @Override
    public Optional<Director> findById(Long id) {
        try {
            return Optional.ofNullable(
                    em.createQuery("select d from Director d where d.id = :id", Director.class)
                            .setParameter("id", id)
                            .getSingleResult()
            );

        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Director> findByName(String name) {
        try {
            return Optional.ofNullable(
                    em.createQuery("select d from Director d where d.name=:name", Director.class)
                            .setParameter("name", name)
                            .getSingleResult()
            );

        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void remove(Director d1) {
        em.remove(d1);
    }
}
