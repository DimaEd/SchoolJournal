package com.ednach.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Global configuration class for all application
 */
@Configuration
@ComponentScan(basePackages = "com.ednach")
@Import({WebConfig.class, DataBaseConfig.class, MessagesConfig.class})
public class AppConfig {
}
