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

    //private String caption; // is it the path to image?

    //private String externalRef;

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

    public Actor() {

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
