package com.contributionplatform.ajoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


//@ComponentScan
//        (basePackages = {"com.contributionplatform.ajoapp.service",
//        "com.contributionplatform.ajoapp.configurations.security.service"})
@SpringBootApplication
public class AjoAppApplication{

    public static void main(String[] args) {
        SpringApplication.run(AjoAppApplication.class, args);
    }
}

