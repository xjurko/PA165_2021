package cz.muni.fi.pa165.rest.controller;

import cz.muni.fi.pa165.dto.MovieDto;
import cz.muni.fi.pa165.dto.MovieRatingDto;
import cz.muni.fi.pa165.dto.Rating;
import cz.muni.fi.pa165.entity.MovieRating;
import cz.muni.fi.pa165.facade.MovieFacade;
import cz.muni.fi.pa165.facade.MovieRatingFacade;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.rest.model.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

/**
 * @author juraj
 */

@RestController
@AllArgsConstructor
@CrossOrigin
@Slf4j
public class MovieController {

    MovieFacade moviesFacade;
    MovieRatingFacade movieRatingsFacade;
    UserFacade userFacade;

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
    List<MovieDto> findMovieByName(@PathVariable String name) {
        return moviesFacade.findMoviesByName(name);
    }


    @RolesAllowed({Role.USER})
    @GetMapping("/user/ratings/{movieId}")
    Optional<MovieRatingDto> getUserRatingForMovie(@PathVariable Long movieId, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return movieRatingsFacade.findRatingByUserAndMovie(userId, movieId);
    }


    @RolesAllowed(Role.USER)
    @GetMapping("/user/ratings/all")
    List<MovieRatingDto> getAllUserRatings(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return movieRatingsFacade.findRatingsByUser(userId);
    }

    @RolesAllowed(Role.USER)
    @DeleteMapping("/user/ratings/{movieId}")
    void removeUserRating(@PathVariable Long movieId, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        movieRatingsFacade.deleteRating(userId, movieId);
    }

    @GetMapping("/movies/{page}")
    List<MovieDto> getMovies(@PathVariable Integer page) {
        return moviesFacade.fetchMovies(page, 10);
    }

    @RolesAllowed(Role.USER)
    @PostMapping("/user/ratings/{movieId}/{rating}")
    void removeUserRating(@PathVariable Long movieId, @PathVariable Rating rating, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        movieRatingsFacade.setRating(new MovieRatingDto(movieId, userId, rating));
    }
}

