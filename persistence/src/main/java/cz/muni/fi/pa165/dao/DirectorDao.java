package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Director;

import java.util.List;
import java.util.Optional;

/**
 * Director DAO interface
 *
 * @author Richard Sanda
 */


public interface DirectorDao {

    /** create/update a director
     *
     * @param d director to be created/updated
     */
    void store(Director d);

    /**
     * Find all actors
     *
     * @return all actors
     */
    List<Director> fetchAll();

    /**
     * Find a director by id
     *
     * @param id id of a director
     * @return director if exists
     */
    Optional<Director> findById(Long id);

    /**
     * Find a director by name
     *
     * @param name name of actor
     * @return director with the given name
     */
    Optional<Director> findByName(String name);

    /**
     * Delete a director
     *
     * @param d1 director to be deleted
     */
    void remove(Director d1);
}
