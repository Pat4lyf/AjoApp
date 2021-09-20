package com.contributionplatform.ajoapp.configurations.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoder {

    //method to encode the password
    @Bean
    public BCryptPasswordEncoder encryptPassword() {
        return new BCryptPasswordEncoder(13);
    }
}

