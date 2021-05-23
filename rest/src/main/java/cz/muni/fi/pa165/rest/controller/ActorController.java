package cz.muni.fi.pa165.rest.controller;

import cz.muni.fi.pa165.dto.ActorDto;
import cz.muni.fi.pa165.facade.ActorFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author juraj
 */

@RestController
@AllArgsConstructor
@CrossOrigin
@Slf4j
public class ActorController {

    ActorFacade actorFacade;

    @GetMapping("/actor/{id}")
    Optional<ActorDto> getActor(@PathVariable Long id) {
        return actorFacade.findActorById(id);
    }
}