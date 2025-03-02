package com.workerp.user_service.config;

import com.workerp.common_lib.exception.BaseGlobalExceptionHandler;
import com.workerp.common_lib.security.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Configuration
@Import({CustomAuthenticationEntryPoint.class})
@ControllerAdvice
public class MicroserviceConfig extends BaseGlobalExceptionHandler {
}