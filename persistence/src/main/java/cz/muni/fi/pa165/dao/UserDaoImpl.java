package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.MovieRating;
import cz.muni.fi.pa165.entity.User;
import lombok.val;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * implementation of UserDao interface
 *
 * @author lsolodkova
 */

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void store(User user) {
        em.persist(user);
    }

    @Override
    public List<User> fetchAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            return Optional.ofNullable(
                    em.find(User.class, id)
            );
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByName(String name) {
        if (name.isBlank())
            throw new IllegalArgumentException("Cannot search for an empty username");
        try {
            return Optional.ofNullable(
                    em.createQuery("select u from User u where name=:name", User.class)
                            .setParameter("name", name)
                            .getSingleResult()
            );
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (email.isBlank())
            throw new IllegalArgumentException("Cannot search for an empty email");
        try {
            return Optional.ofNullable(
                    em.createQuery("select u from User u where email=:email", User.class)
                            .setParameter("email", email)
                            .getSingleResult()
            );
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void remove(User user) {
        val ratings = user.getMovieRatings();
        em.remove(user);
        em.flush();
        for (MovieRating rating : ratings) {
            em.refresh(rating.getMovie());
        }
    }
}
