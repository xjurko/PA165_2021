package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Director;

import java.util.List;
import java.util.Optional;

public interface DirectorDao {

    void store(Director d);

    List<Director> fetchAll();

    Optional<Director> findById(Long id);

    Optional<Director> findByName(String name);

    void remove(Director d1);
}
