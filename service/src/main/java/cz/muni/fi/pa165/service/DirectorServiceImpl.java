package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.DirectorDao;
import cz.muni.fi.pa165.entity.Director;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Richard Sanda
 */

@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService{

    final DirectorDao directorDao;

    @Override
    public Long createDirector(Director director) {
        if (director.getName() == null)
            throw new ValidationException("You cannot create Director with null name.");
        String nameTrimmed = director.getName().trim();
        if (nameTrimmed.isEmpty())
            throw new ValidationException("You cannot create Director with empty name!");
        if (director.getBirthDate() != null && director.getBirthDate().isBefore(LocalDate.now()))
            throw new ValidationException("You cannot create Director with empty or future birthdate.");
        director.setName(nameTrimmed);
        directorDao.store(director);
        return director.getId();
    }

    @Override
    public Optional<Director> findById(Long id) {
        return directorDao.findById(id);
    }

    @Override
    public void deleteDirector(Long directorId) throws DataAccessException {
        Optional<Director> director = directorDao.findById(directorId);

        if (director.isEmpty())
            throw new DataRetrievalFailureException(
                    String.format("No director with id %d in database.!",
                            directorId));
        directorDao.remove(director.get());
    }

    @Override
    public List<Director> findByName(String name) {
        return directorDao.findByName(name);
    }
}
