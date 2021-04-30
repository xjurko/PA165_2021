package cz.muni.fi.pa165.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    @Enumerated(EnumType.STRING)
    private Rating rating;

    public MovieRating(Movie movie, User user, Rating rating) {
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

        if (!getId().equals(that.getId())) return false;
        return getRating() == that.getRating();
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getRating().hashCode();
        return result;
    }
}