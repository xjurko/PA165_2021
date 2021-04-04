package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Actor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Actor DAO implementation
 *
 * @author alia
 */

@Repository
public class ActorDaoImpl implements ActorDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void store(Actor act) {
        em.persist(act);
    }

    @Override
    public List<Actor> fetchAll() {
        return em.createQuery("select act from Actor act", Actor.class)
                .getResultList();
    }

    @Override
    public Optional<Actor> findById(Long id) {
        try {
            return Optional.ofNullable(
                    em.createQuery("select act from Actor act where act.id = :id", Actor.class)
                            .setParameter("id", id)
                            .getSingleResult()
            );

        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Actor> findByFullName(String fullName) {
        return em.createQuery("select act from Actor act where act.fullName = :fullName", Actor.class)
                .setParameter("fullName", fullName)
                .getResultList();
    }

    @Override
    public void remove(Actor act) {
        em.remove(act);
    }
}
