package cz.muni.fi.pa165.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
public class MovieRating {
    @EmbeddedId
    private RatingId id;

    @ManyToOne
    @MapsId("movieId")
    @NonNull
    private Movie movie;

    @ManyToOne
    @MapsId("userId")
    @NonNull
    private User user;

    @NonNull
    @Column(nullable = false)
    private int rating;

    public MovieRating(Movie movie, User user, int rating) {
        this.movie = movie;
        this.user = user;
        this.rating = rating;
        this.id = new RatingId(movie.getId(), user.getId());
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