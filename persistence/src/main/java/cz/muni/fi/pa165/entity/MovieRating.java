package cz.muni.fi.pa165.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class MovieRating {

    @EmbeddedId
    private RatingId id;

    @ManyToOne
    @MapsId("movieId")
    @NotNull
    private Movie movie;

    @ManyToOne
    @MapsId("userId")
    @NotNull
    private User user;

    @NotNull
    @Column(nullable = false)
    private int rating;

    public MovieRating(Movie movie, User user, int rating) {
        this.movie = movie;
        this.user = user;
        this.rating = rating;
        this.id = new RatingId(movie.getId(), user.getId());

        movie.addRating(this);
        user.addRating(this);
    }

    public Movie getMovie() {
        return movie;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieRating)) return false;

        MovieRating that = (MovieRating) o;

        if (rating != that.rating) return false;
        if (!movie.equals(that.movie)) return false;
        return user.equals(that.user);
    }

    @Override
    public int hashCode() {
        int result = movie.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + rating;
        return result;
    }
}