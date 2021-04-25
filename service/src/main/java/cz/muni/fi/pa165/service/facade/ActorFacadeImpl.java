package cz.muni.fi.pa165.service.facade;


import cz.muni.fi.pa165.dto.ActorDto;
import cz.muni.fi.pa165.facade.ActorFacade;
import cz.muni.fi.pa165.service.ActorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author alia
 */

public class ActorFacadeImpl implements ActorFacade {

    final ActorService actorService;
    final BeanConverter converter;

    final static Logger logger = LoggerFactory.getLogger(ActorFacadeImpl.class);


    @Override
    public Long createActor(ActorDto a) {
        return null;
    }

    @Override
    public void deleteActor(Long actorId) {

    }

    @Override
    public Optional<ActorDto> findActorById(Long id) {
        return Optional.empty();
    }

    @Override
    public Iterable<ActorDto> findActorsByFullName(String fullName) {
        return null;
    }

}
