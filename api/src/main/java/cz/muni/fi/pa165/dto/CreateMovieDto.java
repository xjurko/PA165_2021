package cz.muni.fi.pa165.dto;

import java.util.Set;

/**
 * @author juraj
 * <p>
 * used for creating new movies
 */

public class CreateMovieDto {
    String name;
    String caption;
    Integer releaseYear;
    Integer runtimeMin;
    Set<Long> actors;
    Set<Long> directors;
}
