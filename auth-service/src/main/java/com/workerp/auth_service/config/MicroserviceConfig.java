package com.workerp.auth_service.config;

import com.workerp.common_lib.config.BaseJWTConfig;
import com.workerp.common_lib.config.BaseRedisConfig;
import com.workerp.common_lib.exception.BaseGlobalExceptionHandler;
import com.workerp.common_lib.security.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Configuration
@Import({BaseJWTConfig.class, BaseRedisConfig.class, CustomAuthenticationEntryPoint.class})
@ControllerAdvice
public class MicroserviceConfig extends BaseGlobalExceptionHandler {}
