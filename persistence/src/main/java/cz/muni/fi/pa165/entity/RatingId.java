package cz.muni.fi.pa165.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RatingId implements Serializable {
    private Long movieId;
    private Long userId;

    public RatingId(Long movieId, Long userId) {
        this.movieId = movieId;
        this.userId = userId;
    }
}