package com.contributionplatform.ajoapp.configurations.security.config;


public class SecurityAuthorizationConstants {

    public static final long TOKEN_EXPIRATION_TIME = 240_000_000;

    public static final String[] PUBLIC_URIS = new String[]{
            "/",
            // -- Swagger UI v3 (OpenAPI) Start
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui/login/",
            "/swagger-ui/api/login/",
            "/swagger-ui/#/**",
            // -- Swagger UI v3 (OpenAPI) End
            "/login",
            "/api/login",
            "/register",
            "/api/register",
            "/api/register_admin"
    };
}

