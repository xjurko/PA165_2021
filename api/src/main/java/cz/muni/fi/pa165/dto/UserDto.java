package cz.muni.fi.pa165.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * A default user DTO
 * @author lsolodkova
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String passwordHash;
    private String email;
    private Set<MovieRatingDto> movieRatings = new HashSet<>();
}
