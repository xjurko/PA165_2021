package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.DirectorDto;
import cz.muni.fi.pa165.entity.Director;
import cz.muni.fi.pa165.facade.DirectorFacade;
import cz.muni.fi.pa165.service.DirectorService;
import cz.muni.fi.pa165.service.converter.BeanConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class DirectorFacadeImpl implements DirectorFacade {

    final DirectorService directorService;
    final BeanConverter converter;

    @Override
    public Long createDirector(DirectorDto director) throws ValidationException {
        return directorService.createDirector(converter.convert(director.withId(null), Director.class));
    }

    @Override
    public void deleteDirector(Long directorId) {
        directorService.deleteDirector(directorId);
    }

    @Override
    public Optional<DirectorDto> findById(Long directorId) {
        return directorService.findById(directorId).map(director ->
                converter.convert(director, DirectorDto.class));
    }

    @Override
    public Iterable<DirectorDto> findByName(String name) {
        return converter.convert(directorService.findByName(name), DirectorDto.class);
    }
}
