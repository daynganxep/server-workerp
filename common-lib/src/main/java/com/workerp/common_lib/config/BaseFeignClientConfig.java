package com.workerp.common_lib.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.exception.AppException;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class BaseFeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getCredentials() != null) {
                requestTemplate.header("Authorization", "Bearer " + authentication.getCredentials().toString());
            }
            requestTemplate.header("Content-Type", "application/json");
        };
    }

    @Bean
    public ErrorDecoder errorDecoder(ObjectMapper objectMapper) {
        return (methodKey, response) -> {
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