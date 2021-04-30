package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.DirectorDto;

import javax.validation.ValidationException;
import java.util.Optional;

/**
 * Director facade layer interface
 *
 * @author Richard Sanda
 */

public interface DirectorFacade {

    /**
     * Create a Director
     *
     * @param director to be created
     * @return id of created director
     * @throws ValidationException is thrown in case when name or birthdate is empty
     */
    Long createDirector(DirectorDto director) throws ValidationException;

    /**
     * Delete director
     *
     * @param directorId of director to be deleted
     */
    void deleteDirector(Long directorId);

    /**
     * Find director by Id
     *
     * @param directorId of director to be find
     * @return director with given Id
     */
    Optional<DirectorDto> findById(Long directorId);

    /**
     * Find a director by full name
     *
     * @param name of director
     * @return directors with given name
     */
    Iterable<DirectorDto> findByName(String name);
}
