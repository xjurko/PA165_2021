package cz.muni.fi.pa165.service.facade;


import cz.muni.fi.pa165.dto.ActorDto;
import cz.muni.fi.pa165.entity.Actor;
import cz.muni.fi.pa165.exceptions.ValidationException;
import cz.muni.fi.pa165.facade.ActorFacade;
import cz.muni.fi.pa165.service.ActorService;
import cz.muni.fi.pa165.service.converter.BeanConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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
    public Long createActor(ActorDto a) throws ValidationException {
        return actorService.createActor(converter.convert(a, Actor.class));
    }

    @Override
    public void deleteActor(Long actorId) throws ValidationException {
        actorService.deleteActor(actorId);
    }

    @Override
    public Optional<ActorDto> findActorById(Long id) {
        return actorService.findActorById(id).map(a ->
                converter.convert(a, ActorDto.class));
    }

    @Override
    public Iterable<ActorDto> findActorsByFullName(String fullName) {
        return converter.convert(actorService.findActorsByFullName(fullName), ActorDto.class);
    }
}
