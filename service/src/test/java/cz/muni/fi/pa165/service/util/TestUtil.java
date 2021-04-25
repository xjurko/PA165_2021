package cz.muni.fi.pa165.service.util;

import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.User;

import java.util.HashSet;
import java.util.Set;

/**
 * @author juraj
 */
public class TestUtil {
    public static Movie getFakeMovie(Long id, String name) {
        return new Movie(id, name, new HashSet<>(), new HashSet<>(), 0, Set.of(Genre.ACTION), new HashSet<>(), "test caption", "test ref");
    }


    public static User getFakeUser(Long id, String name) {
        return new User(id, name, name + "@muni.cz", new HashSet<>());
    }

}
