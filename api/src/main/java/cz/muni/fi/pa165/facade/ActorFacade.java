package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.ActorDto;
import cz.muni.fi.pa165.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;

/**
 * @author alia
 */

public interface ActorFacade {
    Long createActor(ActorDto a) throws ValidationException;
    void deleteActor(Long actorId) throws ValidationException;
    Optional<ActorDto> findActorById(Long id);
    Iterable<ActorDto> findActorsByFullName(String fullName);
}
