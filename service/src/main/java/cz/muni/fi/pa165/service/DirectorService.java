package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dto.DirectorDto;
import cz.muni.fi.pa165.entity.Actor;
import cz.muni.fi.pa165.entity.Director;
import org.springframework.dao.DataAccessException;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;


public interface DirectorService {
    Optional<Director> findById(Long directorId);
}
