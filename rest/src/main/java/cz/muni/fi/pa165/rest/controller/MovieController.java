package cz.muni.fi.pa165.rest.controller;

import cz.muni.fi.pa165.dto.MovieDto;
import cz.muni.fi.pa165.facade.MovieFacade;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author juraj
 */

@RestController
@CrossOrigin
@AllArgsConstructor
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

    @GetMapping("/user/recommend/{id}")
    List<MovieDto> getMoviesForUser(@PathVariable Long id) {
        return moviesFacade.findRecommendedMoviesForUser(id);
    }

    @GetMapping("/movie/find/{name}")
    List<MovieDto> findMovie(@PathVariable String name) {
        return moviesFacade.findMoviesByName(name);
    }
}

