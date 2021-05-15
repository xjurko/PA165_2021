package cz.muni.fi.pa165.rest.controller;

import cz.muni.fi.pa165.dto.MovieDto;
import cz.muni.fi.pa165.facade.MovieFacade;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

/**
 * @author juraj
 */

@RestController
@AllArgsConstructor
public class MovieController {

    MovieFacade moviesFacade;

    @GetMapping("/movie/{id}")
    Optional<MovieDto> getMovie(@PathVariable Long id) {
        return moviesFacade.getMovieById(id);
    }
}

