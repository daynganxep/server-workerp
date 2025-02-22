package com.workerp.auth_service.config;

import com.workerp.common_lib.config.BaseSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
public class SecurityConfig extends BaseSecurityConfig {
    public SecurityConfig(JwtDecoder jwtDecoder, JwtAuthenticationConverter jwtAuthenticationConverter) {
        super(jwtDecoder, jwtAuthenticationConverter);
        this.POST_PUBLIC_ROUTES = new String[]{"/auth/**", "/", "/login", "/oauth2/**"};
        this.GET_PUBLIC_ROUTES = new String[]{"/auth/**", "/", "/login", "/oauth2/**"};
    }
}