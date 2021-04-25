package cz.muni.fi.pa165.service.config;


import com.github.dozermapper.springboot.autoconfigure.DozerAutoConfiguration;
import cz.muni.fi.pa165.PersistenceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({PersistenceConfig.class, DozerAutoConfiguration.class})
@ComponentScan(basePackages = "cz.muni.fi.pa165")
public class ServiceConfig {
}