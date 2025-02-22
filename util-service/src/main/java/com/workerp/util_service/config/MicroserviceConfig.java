package com.workerp.util_service.config;

import com.workerp.common_lib.config.BaseJWTConfig;
import com.workerp.common_lib.exception.BaseGlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Configuration
@Import({BaseJWTConfig.class})
@ControllerAdvice
public class MicroserviceConfig extends BaseGlobalExceptionHandler {}
