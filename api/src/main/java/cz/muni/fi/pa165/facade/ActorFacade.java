package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.ActorDto;

import java.util.Optional;

/**
 * @author alia
 */

public interface ActorFacade {
    Long createActor(ActorDto a);
    void deleteActor(Long actorId);
    Optional<ActorDto> findActorById(Long id);
    Iterable<ActorDto> findActorsByFullName(String fullName);
}
