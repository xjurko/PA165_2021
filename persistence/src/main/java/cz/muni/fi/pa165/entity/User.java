package cz.muni.fi.pa165.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * User entity
 *
 * @author lsolodkova
 */

@Entity
@Table(name="Users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*TODO: add constraint for non-blank name: size>=1, no spaces-only*/
    @NonNull
    @Column(nullable = false, unique=true)
    private String name;

    /*TODO: add some regexp to constraint email*/
    @NonNull
    @Column(nullable = false, unique=true)
    private String email;

    @OneToMany(mappedBy = "movie")
    private Set<MovieRating> movieRatings = new HashSet<>();

    public User(String name, String email){
        this.name = name;
        this.email = email;
    }

    public void addRating(MovieRating rating) {
        this.movieRatings.add(rating);
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof User)) return false;

        final User user = (User) other;

        if(!user.getName().equals(this.getName())) return false;
        if(!user.getEmail().equals(this.getEmail())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }
}
