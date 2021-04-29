package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Actor;
import org.springframework.dao.DataAccessException;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

/**
 * Actor Service layer interface
 *
 * @author alia
 */

public interface ActorService {

    /**
     * Create an actor
     *
     * @param actor actor to be created
     * @return id of created actor
     * @throws ValidationException is thrown in case when name is empty or height is negative
     */
    Long createActor(Actor actor) throws ValidationException;

    /**
     * Delete an actor
     *
     * @param actorId id of actor to be deleted
     * @throws DataAccessException is thrown when id not found
     */
    void deleteActor(Long actorId) throws DataAccessException;

    /**
     * Find actor by id
     *
     * @param actorId id of actor to be found
     * @return actor with the given id
     */
    Optional<Actor> findActorById(Long actorId);

    /**
     * Find actors by name
     *
     * @param fullName name of actor
     * @return actors with the given name
     */
    List<Actor> findActorsByFullName(String fullName);
}
