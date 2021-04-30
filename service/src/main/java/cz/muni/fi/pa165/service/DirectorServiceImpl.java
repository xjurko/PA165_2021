package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Director;
import org.springframework.stereotype.Service;

import java.util.Optional;

//stub
@Service
public class DirectorServiceImpl implements DirectorService {
    @Override
    public Optional<Director> findById(Long directorId) {
        return Optional.empty();
    }
}
