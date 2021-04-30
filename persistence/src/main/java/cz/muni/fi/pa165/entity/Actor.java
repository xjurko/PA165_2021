package cz.muni.fi.pa165.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Actor entity
 *
 * @author alia
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@With
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String fullName;

    private int height;

    private LocalDate birthDate;

    private LocalDate deathDate;

    @ManyToMany
    private Set<Movie> movies = new HashSet<>();

    public Actor(String fullName,
                 int height,
                 LocalDate birthDate,
                 LocalDate deathDate
    ) {

        this.fullName = fullName;
        this.height = height;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }

    public Actor() {
    }

    public Actor(String fullName) {
        this.fullName = fullName;
    }

    public void addMovie(Movie movie) {
        addMovie(movie, true);
    }

    void addMovie(Movie movie, Boolean propagate) {
        this.movies.add(movie);
        if (propagate) movie.addCastMember(this, false);
    }

    public void removeMovie(Movie movie) {
        removeMovie(movie, true);
    }

    void removeMovie(Movie movie, boolean propagate) {
        this.movies.remove(movie);
        if (propagate) movie.removeCastMember(this);
    }

    @PreRemove
    private void removeActorFromMovies() {
        for (Movie movie : this.movies) {
            movie.removeCastMember(this, false);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actor)) return false;

        Actor actor = (Actor) o;

        if (!fullName.equals(actor.fullName)) return false;
        return birthDate != null ? birthDate.equals(actor.birthDate) : actor.birthDate == null;
    }

    @Override
    public int hashCode() {
        int result = fullName.hashCode();
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        return result;
    }
}
