package com.ednach.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration of Swagger 2
 */
@EnableSwagger2
public class SwaggerConfiguration implements WebMvcConfigurer {

    Contact contact = new Contact("Dmitry Ednach", "", "ednach1987@mail.ru");

    List<VendorExtension> vendorExtensions = new ArrayList<>();

    ApiInfo apiInfo = new ApiInfo(
            "An informational app about the school journal and their students, schedule, sins, grades, and other information. RESTful Web Service documentation. For authorization use \"email\": \"petrovaMaria@mail.ru\", \"password\": \"1234\"." ,
            "This pages documents Info about shool journal RESTful Web Service endpoints", "1.0",
            "localhost:8080/SJ/termsOfService", contact,
            "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", vendorExtensions
    );


    /**
     * Defining Docket Bean
     *
     * @return - new Docket instance
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .securitySchemes(Lists.newArrayList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Configuration of resource handlers for Swagger UI
     *
     * @param registry - ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private ApiKey apiKey(){
        return new ApiKey("Bearer", "Authorization", "header");
    }
}