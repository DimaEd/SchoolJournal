package com.ednach.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Configuration class for resolving messages inside of application
 */
public class MessagesConfig {
    /**
     * Creates Bean from MessageSource interface. Resolves messages,
     * with support for the parameterization and internationalization of such messages
     *
     * @return - MessageSource instance
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("/applicationMessages");
        return messageSource;
    }
}
