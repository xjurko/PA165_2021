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
 * @author Richard Sanda
 */

@Entity
@Getter
@Setter
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @ManyToMany
    private Set<Movie> movies = new HashSet<>();

    private LocalDate birthDate;

    public Director(String name) {
        this.name = name;
    }

    public Director() {
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
        movie.getDirectors().add(this);
    }

    public void removeMovie(Movie movie) {
        this.movies.remove(movie);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Director)) return false;

        Director director = (Director) o;

        if (!name.equals(director.name)) return false;
        return birthDate != null ? birthDate.equals(director.birthDate) : director.birthDate == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        return result;
    }
}
