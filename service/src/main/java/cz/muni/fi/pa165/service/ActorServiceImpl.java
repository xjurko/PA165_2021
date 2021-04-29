package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.ActorDao;
import cz.muni.fi.pa165.entity.Actor;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataAccessException;

import javax.validation.ValidationException;
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
    public Long createActor(Actor actor) throws ValidationException {
        if (actor.getFullName() == null)
            throw new ValidationException("name is null");
        String fullNameTrimmed = actor.getFullName().trim();
        if (fullNameTrimmed.isEmpty())
            throw new ValidationException("name is empty");
        if (actor.getHeight() < 0)
            throw new ValidationException("height is less than 0");
        if (actor.getBirthDate() != null && actor.getDeathDate() != null && actor.getBirthDate().isAfter(actor.getDeathDate()))
            throw new ValidationException("death is before birth");
        actor.setFullName(fullNameTrimmed);
        actorDao.store(actor);
        return actor.getId();
    }

    @Override
    public void deleteActor(Long actorId) throws DataAccessException {
        Optional<Actor> actor = actorDao.findById(actorId);

        if (actor.isEmpty())
            throw new DataRetrievalFailureException(
                    String.format("no actor with id %d in database",
                            actorId));
        actorDao.remove(actor.get());
    }

    @Override
    public Optional<Actor> findActorById(Long actorId) {
        return actorDao.findById(actorId);
    }

    @Override
    public List<Actor> findActorsByFullName(String fullName) {
        return actorDao.findByFullName(fullName);
    }

}
