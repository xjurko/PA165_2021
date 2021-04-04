package cz.muni.fi.pa165.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Actor entity
 *
 * @author alia
 */

@Entity
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String fullName;

    private Double height;

    private LocalDate birthDate;

    private LocalDate deathDate;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Genre> genres;

    @ManyToMany
    private Set<Movie> movies;

    public Actor(@NonNull String fullName,
                 Double height,
                 LocalDate birthDate,
                 LocalDate deathDate,
                 Set<Genre> genres,
                 Set<Movie> movies) {

        this.fullName = fullName;
        this.height = height;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.genres = genres;
        this.movies = movies;
    }

    public Actor() {}

    public Actor(@NonNull String fullName) {
        this.fullName = fullName;
    }

    @NonNull
    public String getFullName() {
        return fullName;
    }

    public void setFullName(@NonNull String fullName) {
        this.fullName = fullName;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public void removeGenre(Genre genre) {
        this.genres.remove(genre);
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
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
