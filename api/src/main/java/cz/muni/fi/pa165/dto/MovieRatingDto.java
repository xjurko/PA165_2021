package cz.muni.fi.pa165.dto;

import lombok.*;

/**
 * @author alia
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class MovieRatingDto {
    Long movieId;
    Long userId;
    Rating rating;
}
