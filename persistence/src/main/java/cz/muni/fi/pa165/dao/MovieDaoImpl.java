package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Movie;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Repository
public class MovieDaoImpl implements MovieDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void store(Movie m) {
        em.persist(m);
    }

    @Override
    public List<Movie> fetchAll() {
        return em.createQuery("select m from Movie m", Movie.class).getResultList();
    }

    @Override
    public List<Movie> findByName(String name) {
        return em.createQuery("select m from Movie m where m.name = :name", Movie.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public Optional<Movie> findById(Long id) {
        try {
            return Optional.ofNullable(
                    em.createQuery("select m from Movie m where m.name = :id", Movie.class)
                            .setParameter("name", id)
                            .getSingleResult()
            );

        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void remove(Movie m) {
        em.remove(m);
    }
}
