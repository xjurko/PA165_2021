package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Director;
import org.springframework.dao.DataAccessException;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

/**
 * Director Service layer interface
 *
 * @author Richard Sanda
 */

public interface DirectorService{

    /**
     * Create a director
     *
     * @param director to be created
     * @return id of created director
     * @throws ValidationException is thrown in case when name is empty
     */
    Long createDirector(Director director) throws ValidationException;

    /**
     * Find director by id
     *
     * @param id of director to find
     * @return director with the given id
     */
    Optional<Director> findById(Long id);

    /**
     * Delete director with given ID
     *
     * @param directorId of director to be deleted
     * @throws DataAccessException is thrown when id not found
     */
    void deleteDirector(Long directorId) throws DataAccessException;

    /**
     * Find directors by name
     *
     * @param name name of director
     * @return directors with the given name
     */
    List<Director> findByName(String name);
}
