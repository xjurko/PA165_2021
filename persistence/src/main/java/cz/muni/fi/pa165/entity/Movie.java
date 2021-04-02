package cz.muni.fi.pa165.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Set;

/**
 * Movie entity
 *
 * @author juraj
 */

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "movies")
    private Set<Actor> cast;
    @ManyToMany(mappedBy = "movies")
    private Set<Director> directors;

    private int runtimeMin;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Genre> genres;

    private String caption;

    private String externalRef;

    ///release data or year


    public Movie(String name,
                 Set<Actor> cast,
                 Set<Director> directors,
                 int runtimeMin,
                 Set<Genre> genres,
                 String caption,
                 String externalRef) {

        this.name = name;
        this.cast = cast;
        this.directors = directors;
        this.runtimeMin = runtimeMin;
        this.genres = genres;
        this.caption = caption;
        this.externalRef = externalRef;
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
