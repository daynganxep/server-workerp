package com.workerp.user_service.config;

import com.workerp.common_lib.config.BaseSecurityConfig;
import com.workerp.common_lib.security.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
public class SecurityConfig extends BaseSecurityConfig {
    public SecurityConfig(JwtDecoder jwtDecoder, JwtAuthenticationConverter jwtAuthenticationConverter, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        super(jwtDecoder, jwtAuthenticationConverter,customAuthenticationEntryPoint);
        this.publicRoutes = new String[]{"api/users/**"};
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
