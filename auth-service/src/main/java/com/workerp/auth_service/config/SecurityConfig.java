package com.workerp.auth_service.config;

import com.workerp.auth_service.security.CustomOAuth2LoginSuccessHandler;
import com.workerp.auth_service.security.CustomOAuth2UserService;
import com.workerp.common_lib.config.BaseSecurityConfig;
import com.workerp.common_lib.security.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig extends BaseSecurityConfig {
    private final CustomOAuth2UserService oAuth2UserService;
    private final CustomOAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    public SecurityConfig(JwtDecoder jwtDecoder, JwtAuthenticationConverter jwtAuthenticationConverter,
                          CustomAuthenticationEntryPoint authenticationEntryPoint, CustomOAuth2UserService oAuth2UserService, CustomOAuth2LoginSuccessHandler oAuth2LoginSuccessHandler) {
        super(jwtDecoder, jwtAuthenticationConverter, authenticationEntryPoint);
        this.oAuth2UserService = oAuth2UserService;
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
        this.publicRoutes = new String[]{
                "/auth/**", "/", "/oauth2/**", "/login/**",
                "/auth/login/oauth2/success", "/oauth2/callback/**"
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Configuring SecurityFilterChain...");
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint))
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/", "/oauth2/**", "/login/**", "/oauth2/callback/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService))
                        .defaultSuccessUrl("/auth/login/oauth2/success", true)
//                        .successHandler(oAuth2LoginSuccessHandler)
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .decoder(jwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter))
                        .authenticationEntryPoint(authenticationEntryPoint))
                .headers(headers -> headers
                        .contentTypeOptions(contentType -> contentType.disable())
                        .frameOptions(frame -> frame.disable()))
                .requiresChannel(channel -> channel
                        .requestMatchers(r -> r.getHeader("Accept") == null ||
                                !r.getHeader("Accept").contains(MediaType.APPLICATION_JSON_VALUE)));

        return http.build();
    }
}
