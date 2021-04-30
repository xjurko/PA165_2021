package cz.muni.fi.pa165.dto;

import lombok.*;

import java.util.Set;

/**
 * @author juraj
 *
 * DTO to be used as a return type when retrieving movies
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    Long id;
    String name;
    String caption;
    Integer releaseYear;
    Integer runtimeMin;
    Set<ActorDto> cast;
    Set<DirectorDto> directors;
}
