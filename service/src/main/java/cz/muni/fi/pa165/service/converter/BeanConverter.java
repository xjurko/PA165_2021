package cz.muni.fi.pa165.service.converter;

/**
 * @author juraj
 */
public interface BeanConverter {
    <T, F> T convert(F from, Class<T> to);
}
