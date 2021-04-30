package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Actor;
import cz.muni.fi.pa165.entity.MovieRating;
import cz.muni.fi.pa165.entity.RatingId;
import lombok.val;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

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
    public Optional<MovieRating> findById(RatingId raitingId) {
        try {
            return Optional.ofNullable(
                    em.createQuery("select mr from MovieRating mr where mr.rating = :ratingId", MovieRating.class)
                            .setParameter("ratingId", raitingId)
                            .getSingleResult()
            );

        } catch (NoResultException e) {
            return Optional.empty();
        }
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
