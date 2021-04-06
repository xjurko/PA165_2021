package cz.muni.fi.pa165.entity;

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

    public void setName(String fullName) {
        this.name = fullName;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void addMovie(Movie movie){
        this.movies.add(movie);
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Director)) return false;

        Director director = (Director) o;

        if (!name.equals(director.name)) return false;
        return birthDate != null ? birthDate.equals(director.birthDate) : director.birthDate == null;
    }
}
