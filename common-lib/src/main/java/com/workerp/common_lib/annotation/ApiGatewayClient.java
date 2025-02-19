package com.workerp.common_lib.annotation;

import com.workerp.common_lib.config.BaseFeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@FeignClient(
    name = "api-gateway",
    url = "http://localhost:8080",
    configuration = BaseFeignClientConfig.class
)
public @interface ApiGatewayClient {}