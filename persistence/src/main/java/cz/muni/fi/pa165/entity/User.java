package cz.muni.fi.pa165.entity;

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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, unique=true)
    private String name;

    @NonNull
    @Column(nullable = false, unique=true)
    private String email;

    @OneToMany(mappedBy = "movie")
    private Set<MovieRating> movieRatings = new HashSet<>();

    public User(String name, String email){
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() { return name; }

    public String getEmail() { return email; }

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
