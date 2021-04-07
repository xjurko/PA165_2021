package cz.muni.fi.pa165.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
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

    @NonNull
    @Column(nullable = false)
    private String name;

    @ManyToMany
    private Set<Movie> movies;

    private LocalDate birthDate;

    public Director(@NonNull String name) {
        this.name = name;
    }

    public Director() { }

    public Director(@NonNull String name,
                    Set<Movie> movies,
                    LocalDate birthDate) {
        this.name = name;
        this.movies = movies;
        this.birthDate = birthDate;
    }

    public void addMovie(Movie movie){
        this.movies.add(movie);
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
