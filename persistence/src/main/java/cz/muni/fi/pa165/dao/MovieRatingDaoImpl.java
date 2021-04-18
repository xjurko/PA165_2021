package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.MovieRating;
import lombok.val;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Implementation of MovieRatingDao
 *
 * @author juraj
 */

@Repository
public class MovieRatingDaoImpl implements MovieRatingDao {
    @PersistenceContext
    private EntityManager em;


    @Override
    public void store(MovieRating rating) {
        em.persist(rating);
    }

    @Override
    public void remove(MovieRating rating) {
        val movie = rating.getMovie();
        val user = rating.getUser();
        em.remove(rating);
        em.flush();
        em.refresh(user);
        em.refresh(movie);
    }
}
