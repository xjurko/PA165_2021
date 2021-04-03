package cz.muni.fi.pa165.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String email;

    @OneToMany(mappedBy = "movie")
    private Set<UserMovieRating> movieRatings = new HashSet<>();


    public User(String userName, String email){
        this.userName = userName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }
}
