package com.ednach.config;


import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

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

    /**
     * Create Bean from service interface for encoding passwords
     *
     * @return - BCryptPasswordEncoder.
     * Implementation of PasswordEncoder that uses the BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates Bean that generates views by setted parameters
     *
     * @return - InternalResourceViewResolver instance
     */
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
