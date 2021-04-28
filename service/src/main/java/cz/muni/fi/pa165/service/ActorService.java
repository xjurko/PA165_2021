package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Actor;
import cz.muni.fi.pa165.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;

public interface ActorService {
    Long createActor(Actor a) throws ValidationException;
    void deleteActor(Long actorId) throws ValidationException;
    Optional<Actor> findActorById(Long id);
    List<Actor> findActorsByFullName(String fullName);
}
