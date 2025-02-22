package com.workerp.util_service.config;

import com.workerp.common_lib.config.BaseSecurityConfig;
import com.workerp.common_lib.security.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig extends BaseSecurityConfig {
    public SecurityConfig(JwtDecoder jwtDecoder, JwtAuthenticationConverter jwtAuthenticationConverter, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        super(jwtDecoder, jwtAuthenticationConverter, customAuthenticationEntryPoint);
        this.publicRoutes = new String[]{"/api/utils/**"};
    }
}
