package cz.muni.fi.pa165.entity;

import javax.persistence.*;

@Entity
public class UserMovieRating {

    @EmbeddedId
    private RatingId id;

    @ManyToOne
    @MapsId("movieId")
    private Movie movie;

    @ManyToOne
    @MapsId("userId")
    private User user;

    private int rating;
}