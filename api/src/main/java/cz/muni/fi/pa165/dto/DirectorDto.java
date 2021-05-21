package cz.muni.fi.pa165.dto;

import lombok.*;

import java.time.LocalDate;

/**
 * @author Richard Sanda
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class DirectorDto {
    Long id;
    String name;
    Integer birthYear;
    Integer deathYear;
    String posterUrl;
}
