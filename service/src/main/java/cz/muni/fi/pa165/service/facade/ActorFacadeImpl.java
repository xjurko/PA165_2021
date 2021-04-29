package cz.muni.fi.pa165.service.facade;


import cz.muni.fi.pa165.dto.ActorDto;
import cz.muni.fi.pa165.entity.Actor;
import cz.muni.fi.pa165.facade.ActorFacade;
import cz.muni.fi.pa165.service.ActorService;
import cz.muni.fi.pa165.service.converter.BeanConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.ValidationException;
import java.util.Optional;

/**
 * @author alia
 */

@RequiredArgsConstructor
@Service
@Transactional
public class ActorFacadeImpl implements ActorFacade {

    final ActorService actorService;
    final BeanConverter converter;

    @Override
    public Long createActor(ActorDto actor) throws ValidationException {
        return actorService.createActor(converter.convert(actor, Actor.class));
    }

    @Override
    public void deleteActor(Long actorId){
        actorService.deleteActor(actorId);
    }

    @Override
    public Optional<ActorDto> findActorById(Long actorId) {
        return actorService.findActorById(actorId).map(actor ->
                converter.convert(actor, ActorDto.class));
    }

    @Override
    public Iterable<ActorDto> findActorsByFullName(String fullName) {
        return converter.convert(actorService.findActorsByFullName(fullName), ActorDto.class);
    }
}
