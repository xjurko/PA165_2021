package cz.muni.fi.pa165.rest.controller;

import cz.muni.fi.pa165.dto.MovieDto;
import cz.muni.fi.pa165.facade.MovieFacade;
import cz.muni.fi.pa165.rest.model.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * @author juraj
 */

@RestController
@AllArgsConstructor
@Slf4j
public class MovieController {

    MovieFacade moviesFacade;

    @GetMapping("/movie/{id}")
    Optional<MovieDto> getMovie(@PathVariable Long id) {
        return moviesFacade.getMovieById(id);
    }

    @GetMapping("/movie/recommend/{id}")
    List<MovieDto> getMoviesForMovie(@PathVariable Long id) {
        return moviesFacade.findRecommendedMoviesBasedOnMovie(id);
    }

    @RolesAllowed({Role.USER})
    @GetMapping("/user/recommend")
    List<MovieDto> getMoviesForUser(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return moviesFacade.findRecommendedMoviesForUser(userId);
    }

    @GetMapping("/movie/find/{name}")
    List<MovieDto> findMovie(@PathVariable String name) {
        return moviesFacade.findMoviesByName(name);
    }
}

