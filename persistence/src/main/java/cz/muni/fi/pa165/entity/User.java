package cz.muni.fi.pa165.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;

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
    private String userName;

    @NonNull
    @Column(nullable = false, unique=true)
    private String email;

    public User(String userName, String email){
        this.userName = userName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() { return userName; }

    public String getEmail() { return email; }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof User)) return false;

        final User u = (User) o;

        if(!u.getUserName().equals(this.getUserName())) return false;
        if(!u.getEmail().equals(this.getEmail())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, email);
    }
}
