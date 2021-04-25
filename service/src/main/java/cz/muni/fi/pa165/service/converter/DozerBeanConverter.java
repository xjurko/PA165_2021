package cz.muni.fi.pa165.service.converter;

import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;


/**
 * @author juraj
 */

@Named("DozerConverter")
@RequiredArgsConstructor
public class DozerBeanConverter implements BeanConverter {
    final Mapper mapper;

    @Override
    public <T, F> T convert(F from, Class<T> to) {
        return mapper.map(from, to);
    }
}
