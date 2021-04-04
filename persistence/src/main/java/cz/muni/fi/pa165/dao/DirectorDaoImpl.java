package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Director;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DirectorDaoImpl implements DirectorDao{
    @Override
    public void store(Director d) {

    }

    @Override
    public List<Director> fetchAll() {
        return null;
    }

    @Override
    public Optional<Director> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Director> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public void remove(Director d1) {

    }
}
