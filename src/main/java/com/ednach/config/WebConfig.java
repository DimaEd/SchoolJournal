package com.ednach.config;


import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Configuration class of web components. Imports the Spring MVC configuration.
 */
@EnableWebMvc
public class WebConfig {
    /**
     * Bean for programmatic Builder of Dozer mappings for entities
     *
     * @return - DozerBeanMapper instance
     */
    @Bean
    public Mapper mapper() {
        return new DozerBeanMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
