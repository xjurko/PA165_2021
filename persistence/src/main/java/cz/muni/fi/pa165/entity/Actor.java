package cz.muni.fi.pa165.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Actor {

    @Id
    private Long id;

    @ManyToMany
    private Set<Movie> movies;
}
