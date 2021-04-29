package cz.muni.fi.pa165.service.converter;

import com.github.dozermapper.core.Mapper;
import io.vavr.collection.List;
import io.vavr.collection.Vector;
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

    @Override
    public <T, F> java.util.List<T> convert(java.util.List<F> from, Class<T> to) {
        return Vector.ofAll(from).map(e -> convert(e, to)).toList().asJava();
    }
}
