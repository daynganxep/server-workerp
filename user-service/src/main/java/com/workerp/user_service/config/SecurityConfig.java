package com.workerp.user_service.config;

import com.workerp.common_lib.config.BaseSecurityConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig extends BaseSecurityConfig {
    public SecurityConfig(){
        this.GET_PUBLIC_ROUTES = new String[]{"api/users/email/**"};
    }
}
