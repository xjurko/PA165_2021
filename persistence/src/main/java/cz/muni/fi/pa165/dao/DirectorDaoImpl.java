package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Director;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DirectorDaoImpl implements DirectorDao {
    @Override
    public void store(Director d) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Director> fetchAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Director> findById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Director> findByName(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Director d1) {
        throw new UnsupportedOperationException();
    }
}
