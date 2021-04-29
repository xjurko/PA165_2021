package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.ActorDto;
import javax.xml.bind.ValidationException;

import java.util.Optional;

/**
 * Actor Facade layer interface
 *
 * @author alia
 */

public interface ActorFacade {

    /**
     * Create an actor
     *
     * @param actor actor to be created
     * @return id of created actor
     * @throws ValidationException is thrown in case when name is empty or height is negative
     */
    Long createActor(ActorDto actor) throws ValidationException;

    /**
     * Delete an actor
     *
     * @param actorId id of actor to be deleted
     */
    void deleteActor(Long actorId);

    /**
     * Find an actor by id
     *
     * @param actorId id of actor to be found
     * @return actor with the given id
     */
    Optional<ActorDto> findActorById(Long actorId);

    /**
     * Find an actor by full name
     *
     * @param fullName name of actor
     * @return actors with given name
     */
    Iterable<ActorDto> findActorsByFullName(String fullName);
}
