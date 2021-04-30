package cz.muni.fi.pa165.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    //username starts with alphabetical, then any alphanumeric/dash/dot/underscore,
    // ends with alphanumeric, length from 5 to 15
    @Pattern(regexp="^[a-zA-Z]([._-](?![._-])|[a-zA-Z0-9]){5,15}[a-zA-Z0-9]$")
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String passwordHash;

    @NotBlank
    @Pattern(regexp=".+@.+\\....?")
    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "movie",  orphanRemoval = true)
    private Set<MovieRating> movieRatings = new HashSet<>();

    // TODO: possibly replace with a separate roles entity
    private boolean isAdmin;

    public User(String name, String email, String passwordHash) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.isAdmin = false;
    }

    public void addRating(MovieRating rating) {
        this.movieRatings.add(rating);
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
