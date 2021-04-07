package cz.muni.fi.pa165.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Director {
    @Id
    @Column(nullable = false)
    private Long id;

    @ManyToMany
    private Set<Movie> movies = new HashSet<>();

    public Director(String name) {
    }

    public Director() {

    }

    public void setName(String fullName) {
    }

    public Long getId() {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        throw new UnsupportedOperationException();
    }
}
