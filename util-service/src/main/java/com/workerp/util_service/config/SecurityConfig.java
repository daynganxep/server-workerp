package com.workerp.util_service.config;

import com.workerp.common_lib.config.BaseSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig extends BaseSecurityConfig {
    public SecurityConfig(JwtDecoder jwtDecoder, JwtAuthenticationConverter jwtAuthenticationConverter) {
        super(jwtDecoder, jwtAuthenticationConverter);
        this.GET_PUBLIC_ROUTES = new String[]{"/api/utils/**"};
        this.POST_PUBLIC_ROUTES = new String[]{"/api/utils/**"};
        this.DELETE_PUBLIC_ROUTES = new String[]{"/api/utils/**"};
    }
}
