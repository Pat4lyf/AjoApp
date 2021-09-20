package com.contributionplatform.ajoapp.configurations;
/**
 * https://springfox.github.io/springfox/docs/
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket swaggerConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.contributionplatform.ajoapp"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(makeApiInfo());
    }

    private ApiInfo makeApiInfo() {
        return new ApiInfo(
                "Ajo App APIs",
                "description",
                "version",
                "",
                new Contact("Ajo App", "localhost:8080", "admin@admin.com"),
                "MIT",
                "license url",
                Collections.emptyList()
        );
    }
}

