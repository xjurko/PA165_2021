package cz.muni.fi.pa165.service.converter;

import com.github.dozermapper.core.Mapper;
import io.vavr.collection.Vector;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import java.util.List;


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
    public <T, F> List<T> convert(List<F> from, Class<T> to) {
        return Vector.ofAll(from).map(e -> convert(e, to)).toList().asJava();
    }
}
