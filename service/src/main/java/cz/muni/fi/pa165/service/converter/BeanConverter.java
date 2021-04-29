package cz.muni.fi.pa165.service.converter;

import java.util.List;

/**
 * @author juraj
 */
public interface BeanConverter {
    <T, F> T convert(F from, Class<T> to);

    <T, F> List<T> convert(List<F> from, Class<T> to);
}
