package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.ActorDao;
import cz.muni.fi.pa165.entity.Actor;
import cz.muni.fi.pa165.exceptions.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author alia
 */

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService{

    final ActorDao actorDao;

    @Override
    public Long createActor(Actor a) throws ValidationException {
        if (a.getFullName() == null)
            throw new ValidationException("name is null");
        String fullNameTrimmed = a.getFullName().trim();
        if (fullNameTrimmed.isEmpty())
            throw new ValidationException("name is empty");
        if (a.getHeight() != null && a.getHeight() < 1)
            throw new ValidationException("height is less than 1");
        if (a.getBirthDate() != null && a.getDeathDate() != null && a.getBirthDate().isAfter(a.getDeathDate()))
            throw new ValidationException("death is before birth");
        a.setFullName(fullNameTrimmed);
        actorDao.store(a);
        return a.getId();
    }

    @Override
    public void deleteActor(Long actorId) throws ValidationException {
        Optional<Actor> actor = actorDao.findById(actorId);

        if (actor.isEmpty())
            throw new ValidationException(
                    String.format("no actor with id %d in database",
                            actorId));
        actorDao.remove(actor.get());
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
