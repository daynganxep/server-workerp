package com.workerp.common_lib.exception;

import com.workerp.common_lib.dto.api.ApiResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {
    private final HttpStatus status;
    private final String code;

    public AppException(HttpStatus status, String message, String code) {
        super(message);
        this.status = status;
        this.code = code;
    }

    public AppException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.code = null;
    }

    public AppException(String message) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.code = null;
    }

    public AppException(ApiResponse<?> response) {
        super(response.getMessage());
        this.status = HttpStatus.BAD_REQUEST;
        this.code = response.getCode();
    }

}

