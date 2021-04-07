package cz.muni.fi.pa165.entity;

import lombok.Getter;
import lombok.Setter;

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
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String fullName;

    private Double height;

    private LocalDate birthDate;

    private LocalDate deathDate;

    @ManyToMany
    private Set<Movie> movies = new HashSet<>();

    public Actor(String fullName,
                 Double height,
                 LocalDate birthDate,
                 LocalDate deathDate,
                 Set<Movie> movies) {

        this.fullName = fullName;
        this.height = height;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.movies = movies;
    }

    public Actor() {}

    public Actor(String fullName) {
        this.fullName = fullName;
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
        movie.addActor(this);
    }

    public void removeMovie(Movie movie) {
        this.movies.remove(movie);
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
