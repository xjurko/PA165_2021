package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Actor;

import java.util.List;
import java.util.Optional;

/**
 * Actor DAO interface
 *
 * @author alia
 */


public interface ActorDao {

    /** create an actor
     *
     * @param act actor to be created
     */
    void store(Actor act);

    /**
     * Find all actors
     *
     * @return all actors
     */
    List<Actor> fetchAll();

    /**
     * Find an actor by id
     *
     * @param id id of an actor
     * @return actor if it exists
     */
    Optional<Actor> findById(Long id);

    /**
     * Find an actor by full name
     *
     * @param fullName name of actor
     * @return actors with the given name
     */
    List<Actor> findByFullName(String fullName);

    /**
     * Delete an actor
     *
     * @param act actor to be deleted
     */
    void remove(Actor act);
}
