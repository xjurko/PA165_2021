package cz.muni.fi.pa165.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Actor {

    @ManyToMany
    private Set<Movie> movies;
}
