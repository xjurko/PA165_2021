package cz.muni.fi.pa165.dto;

import lombok.*;

import java.util.Set;

/**
 * @author juraj
 */

@With
@Value
public class MovieDto {
    Long id;
    String name;
    String caption;
    Integer releaseYear;
    Integer runtime;
    Set<ActorDto> cast;
    Set<DirectorDto> directors;
    Set<MovieRatingDto> ratings;
}


