package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Actor;

import java.util.List;
import java.util.Optional;

public interface ActorService {
    Long createActor(Actor a);
    void deleteActor(Long actorId);
    Optional<Actor> findActorById(Long id);
    List<Actor> findActorsByFullName(String fullName);
}
