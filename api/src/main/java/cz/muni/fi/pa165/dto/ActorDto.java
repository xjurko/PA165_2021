package cz.muni.fi.pa165.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author alia
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorDto {
    Long id;
    String fullName;
    Double height;
    LocalDate birthDate;
    LocalDate deathDate;
}
