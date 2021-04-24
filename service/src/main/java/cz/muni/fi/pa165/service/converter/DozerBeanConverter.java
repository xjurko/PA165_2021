package cz.muni.fi.pa165.service.converter;

import com.github.dozermapper.core.Mapper;
import cz.muni.fi.pa165.service.facade.MovieFacadeImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;


/**
 * @author juraj
 */

@Named("DozerConverter")
@RequiredArgsConstructor
public class DozerBeanConverter implements BeanConverter {

    final static Logger logger = LoggerFactory.getLogger(DozerBeanConverter.class);
    final Mapper mapper;

    @Override
    public <T, F> T convert(F from, Class<T> to) {
        logger.error("converting {} to {}", from.getClass().toString(), to.toString());
        return mapper.map(from, to);
    }
}
