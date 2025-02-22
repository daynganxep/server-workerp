package com.workerp.user_service.config;

import com.workerp.common_lib.config.BaseSecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
public class SecurityConfig extends BaseSecurityConfig {
    public SecurityConfig(JwtDecoder jwtDecoder, JwtAuthenticationConverter jwtAuthenticationConverter) {
        super(jwtDecoder, jwtAuthenticationConverter);
        this.GET_PUBLIC_ROUTES = new String[]{"api/users/**"};
        this.POST_PUBLIC_ROUTES = new String[]{"api/users/**"};
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
