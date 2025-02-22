package com.workerp.common_lib.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class BaseSecurityConfig {
    private final JwtDecoder jwtDecoder;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    protected String[] GET_PUBLIC_ROUTES = {};
    protected String[] POST_PUBLIC_ROUTES = {};
    protected String[] PUT_PUBLIC_ROUTES = {};
    protected String[] PATCH_PUBLIC_ROUTES = {};
    protected String[] DELETE_PUBLIC_ROUTES = {};
    protected String[] OPTIONS_PUBLIC_ROUTES = {};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, GET_PUBLIC_ROUTES).permitAll()
                        .requestMatchers(HttpMethod.POST, POST_PUBLIC_ROUTES).permitAll()
                        .requestMatchers(HttpMethod.PUT, PUT_PUBLIC_ROUTES).permitAll()
                        .requestMatchers(HttpMethod.PATCH, PATCH_PUBLIC_ROUTES).permitAll()
                        .requestMatchers(HttpMethod.DELETE, DELETE_PUBLIC_ROUTES).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, OPTIONS_PUBLIC_ROUTES).permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .decoder(jwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter)
                        )
                )
                .headers(headers -> headers
                        .contentTypeOptions(contentType -> contentType.disable())
                        .frameOptions(frame -> frame.disable()))
                .requiresChannel(channel -> channel
                        .requestMatchers(r -> r.getHeader("Accept") == null ||
                                !r.getHeader("Accept").contains(MediaType.APPLICATION_JSON_VALUE)));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}