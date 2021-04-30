package cz.muni.fi.pa165.entity;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Movie entity
 *
 * @author juraj
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@With
@ToString
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "movies")
    private Set<Actor> cast = new HashSet<>();

    @ManyToMany(mappedBy = "movies")
    private Set<Director> directors = new HashSet<>();

    private int runtimeMin;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(mappedBy = "movie", orphanRemoval = true)
    private Set<MovieRating> ratings = new HashSet<>();

    private String caption;

    private String externalRef;

    private Integer releaseYear;

    public Movie() {
    }

    public Movie(String name,
                 int runtimeMin,
                 Integer releaseYear,
                 String caption,
                 String externalRef
    ) {

        this.name = name;
        this.runtimeMin = runtimeMin;
        this.caption = caption;
        this.externalRef = externalRef;
        this.releaseYear = releaseYear;
    }

    @PreRemove
    private void removeMovieFromActors() {
        for (Actor actor : this.getCast()) {
            actor.removeMovie(this, false);
        }
    }

    public void addRating(MovieRating rating) {
        this.ratings.add(rating);
    }

    public void addCastMember(Actor actor) {
        addCastMember(actor, true);
    }

    void addCastMember(Actor actor, Boolean propagate) {
        this.cast.add(actor);
        if (propagate) actor.addMovie(this, false);
    }

    public void removeCastMember(Actor actor) {
        removeCastMember(actor, true);
    }

    void removeCastMember(Actor actor, boolean propagate) {
        this.cast.remove(actor);
        if (propagate) actor.removeMovie(this, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        if (runtimeMin != movie.runtimeMin) return false;
        if (!name.equals(movie.name)) return false;
        if (caption != null ? !caption.equals(movie.caption) : movie.caption != null) return false;
        return externalRef != null ? externalRef.equals(movie.externalRef) : movie.externalRef == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + runtimeMin;
        result = 31 * result + (caption != null ? caption.hashCode() : 0);
        result = 31 * result + (externalRef != null ? externalRef.hashCode() : 0);
        return result;
    }
}
