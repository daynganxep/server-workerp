package com.workerp.common_lib.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.exception.AppException;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Configuration
public class BaseFeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String token = request.getHeader("Authorization");
                if (token == null || token.isBlank()) {
                    log.warn("Feign Request: No Authorization header found in HttpServletRequest");
                } else {
                    log.info("Feign Request Token: {}", token);
                    requestTemplate.header("Authorization", token);
                }
            }
            requestTemplate.header("Content-Type", "application/json");
        };
    }

    @Bean
    public ErrorDecoder errorDecoder(ObjectMapper objectMapper) {
        return (methodKey, response) -> {

            // Handle unauthorized with no response body explicitly
            if (response.body() == null) {
                if (response.status() == HttpStatus.UNAUTHORIZED.value()) {
                    return new AppException(
                            HttpStatus.UNAUTHORIZED,
                            "Unauthorized access - token might be invalid or not properly accepted",
                            "service-401-unauthorized"
                    );
                }
                return new AppException(
                        HttpStatus.valueOf(response.status()),
                        "Response body is null",
                        "global-e-null-body"
                );
            }

            ApiResponse<?> apiResponse = null;
            try (InputStream bodyIs = response.body().asInputStream()) {
                String responseBody = new String(bodyIs.readAllBytes());
                System.out.println("Feign Error Response: " + responseBody);
                apiResponse = objectMapper.readValue(responseBody, ApiResponse.class);
            } catch (IOException e) {
                return new AppException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Error parsing response",
                        "service-e-parse"
                );
            }

            if (apiResponse == null) {
                return new AppException(
                        HttpStatus.valueOf(response.status()),
                        "Empty response from service",
                        "service-f-empty"
                );
            }

            return new AppException(
                    HttpStatus.valueOf(response.status()),
                    apiResponse.getMessage() != null ? apiResponse.getMessage() : response.reason(),
                    apiResponse.getCode() != null ? apiResponse.getCode() : "service-f-" + response.status()
            );
        };
    }
}