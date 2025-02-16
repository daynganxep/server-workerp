package com.workerp.common_lib.client;

import com.workerp.common_lib.dto.api.ApiResponse;
import lombok.Getter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Getter
public class RestApi {
    private final String apiGatewayUrl;
    private final RestTemplate restTemplate;

    public RestApi(String apiGatewayUrl) {
        this.apiGatewayUrl = apiGatewayUrl;
        this.restTemplate = new RestTemplate();
    }

    private String getFullUrl(String path) {
        return apiGatewayUrl + path;
    }

    public <T> ApiResponse<T> get(String path, Class<T> responseType) {
        String url = getFullUrl(path);
        ResponseEntity<ApiResponse<T>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<ApiResponse<T>>() {}
        );
        return response.getBody();
    }

    public <T> ApiResponse<T> post(String path, Object request, Class<T> responseType) {
        String url = getFullUrl(path);
        ResponseEntity<ApiResponse<T>> response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            new HttpEntity<>(request),
            new ParameterizedTypeReference<ApiResponse<T>>() {}
        );
        return response.getBody();
    }

    public <T> ApiResponse<T> put(String path, Object request, Class<T> responseType) {
        String url = getFullUrl(path);
        ResponseEntity<ApiResponse<T>> response = restTemplate.exchange(
            url,
            HttpMethod.PUT,
            new HttpEntity<>(request),
            new ParameterizedTypeReference<ApiResponse<T>>() {}
        );
        return response.getBody();
    }

    public <T> ApiResponse<T> delete(String path, Class<T> responseType) {
        String url = getFullUrl(path);
        ResponseEntity<ApiResponse<T>> response = restTemplate.exchange(
            url,
            HttpMethod.DELETE,
            null,
            new ParameterizedTypeReference<ApiResponse<T>>() {}
        );
        return response.getBody();
    }
}
