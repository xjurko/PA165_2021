package cz.muni.fi.pa165.service.config;


import com.github.dozermapper.springboot.autoconfigure.DozerAutoConfiguration;
import cz.muni.fi.pa165.PersistenceConfig;
import org.apache.commons.text.similarity.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@Import({PersistenceConfig.class, DozerAutoConfiguration.class})
@ComponentScan(basePackages = "cz.muni.fi.pa165")
public class ServiceConfig {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public EditDistance<Double> similarityMatcher() {
        return new JaroWinklerDistance();
    }

//    @Bean
//    public EditDistance<?> similarityMatcher() {
//        return new LevenshteinDistance();
//    }
}