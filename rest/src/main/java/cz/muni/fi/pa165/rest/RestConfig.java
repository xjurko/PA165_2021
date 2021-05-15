package cz.muni.fi.pa165.rest;

import cz.muni.fi.pa165.PersistenceConfig;
import cz.muni.fi.pa165.service.config.ServiceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author juraj
 */
@Configuration
@Import({ServiceConfig.class, PersistenceConfig.class})
public class RestConfig {
}


