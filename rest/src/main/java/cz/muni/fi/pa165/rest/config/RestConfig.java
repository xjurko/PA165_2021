package cz.muni.fi.pa165.rest.config;

import cz.muni.fi.pa165.PersistenceConfig;
import cz.muni.fi.pa165.service.config.ServiceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.core.GrantedAuthorityDefaults;

/**
 * @author juraj
 */
@Configuration
@Import({ServiceConfig.class})
public class RestConfig {
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }
}



