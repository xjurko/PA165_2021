package cz.muni.fi.pa165.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * User entity
 *
 * @author lsolodkova
 */

@Entity
@Table(name = "Users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "movie",  orphanRemoval = true)
    private Set<MovieRating> movieRatings = new HashSet<>();

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void addRating(MovieRating rating) {
        this.movieRatings.add(rating);
    }

    void removeRating(MovieRating rating) {
        this.movieRatings.remove(rating);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof User)) return false;

        final User user = (User) other;

        if (!user.getName().equals(this.getName())) return false;
        return user.getEmail().equals(this.getEmail());
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }
}
