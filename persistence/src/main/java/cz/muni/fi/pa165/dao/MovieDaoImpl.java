package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.MovieRating;
import lombok.val;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * implementation of MovieDao
 *
 * @author juraj
 */

@Repository
public class MovieDaoImpl implements MovieDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void store(Movie m) {
        em.persist(m);
    }

    @Override
    public List<Movie> fetchPage(Integer page, Integer pageSize) {
        return em.createQuery("select m from Movie m", Movie.class)
            .setFirstResult((page - 1) * pageSize)
            .setMaxResults(pageSize)
            .getResultList();
    }

    @Override
    public List<Movie> findByName(String name) {
        return em.createQuery("select m from Movie m where upper(m.name) like :name", Movie.class)
            .setParameter("name", MatchMode.ANYWHERE.toMatchString(name.toUpperCase()))
            .getResultList();
    }

    @Override
    public Optional<Movie> findById(Long id) {
        try {
            return Optional.ofNullable(
                em.createQuery("select m from Movie m where m.id = :id", Movie.class)
                    .setParameter("id", id)
                    .getSingleResult()
            );

        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void remove(Movie m) {
        val ratings = m.getRatings();
        em.remove(m);
        em.flush();
        for (MovieRating rating : ratings) {
            em.refresh(rating.getUser());
        }
    }

}
