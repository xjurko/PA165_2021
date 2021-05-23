package cz.muni.fi.pa165.rest.controller;

import cz.muni.fi.pa165.dto.ActorDto;
import cz.muni.fi.pa165.facade.ActorFacade;
import cz.muni.fi.pa165.rest.model.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
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

    @RolesAllowed({Role.ADMIN})
    @DeleteMapping("/actor/{id}")
    void deleteActor(@PathVariable Long id) {
        actorFacade.deleteActor(id);
    }
}