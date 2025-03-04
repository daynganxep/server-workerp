package com.workerp.hr_app_service.config;

import com.workerp.common_lib.config.BaseJWTConfig;
import com.workerp.common_lib.config.BaseRedisConfig;
import com.workerp.common_lib.config.BaseWebConfig;
import com.workerp.common_lib.exception.BaseGlobalExceptionHandler;
import com.workerp.common_lib.interceptor.PermissionInterceptor;
import com.workerp.common_lib.security.CustomAuthenticationEntryPoint;
import com.workerp.common_lib.service.BaseRedisService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Configuration
@Import({BaseJWTConfig.class, BaseRedisConfig.class, BaseRedisService.class, CustomAuthenticationEntryPoint.class, BaseWebConfig.class, PermissionInterceptor.class})
@ControllerAdvice
public class MicroserviceConfig extends BaseGlobalExceptionHandler {
}
