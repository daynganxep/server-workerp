package com.workerp.company_app_service.config;

import com.workerp.common_lib.config.BaseSecurityConfig;
import com.workerp.common_lib.security.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
public class SecurityConfig extends BaseSecurityConfig {
    public SecurityConfig(JwtDecoder jwtDecoder, JwtAuthenticationConverter jwtAuthenticationConverter,
                          CustomAuthenticationEntryPoint authenticationEntryPoint) {
        super(jwtDecoder, jwtAuthenticationConverter, authenticationEntryPoint);
        this.publicRoutes = new String[]{
                "/api/company-app/**"
        };
    }
}
