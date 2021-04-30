package cz.muni.fi.pa165.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author juraj
 * <p>
 * used for creating new movies
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMovieDto {
    String name;
    String caption;
    Integer releaseYear;
    Integer runtimeMin;
    String externalRef;
    Set<Long> actorIds;
    Set<Long> directorIds;
    Set<Genre> genres;
}
