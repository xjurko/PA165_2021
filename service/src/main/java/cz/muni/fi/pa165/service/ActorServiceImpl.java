package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.ActorDao;
import cz.muni.fi.pa165.entity.Actor;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class ActorServiceImpl implements ActorService{

    @Inject
    private ActorDao actorDao;

    @Override
    public Long createActor(Actor a) {
        actorDao.store(a);
        return a.getId();
    }

    @Override
    public void deleteActor(Long actorId) {
        actorDao.findById(actorId).ifPresent(actor -> actorDao.remove(actor));
    }

    @Override
    public Optional<Actor> findActorById(Long id) {
        return actorDao.findById(id);
    }

    @Override
    public List<Actor> findActorsByFullName(String fullName) {
        return actorDao.findByFullName(fullName);
    }

}
