package com.workerp.user_service.config;

import com.workerp.common_lib.config.BaseSecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends BaseSecurityConfig {
    public SecurityConfig(){
        this.GET_PUBLIC_ROUTES = new String[]{"api/users/email/**"};
        this.POST_PUBLIC_ROUTES = new String[]{"api/**"};
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
