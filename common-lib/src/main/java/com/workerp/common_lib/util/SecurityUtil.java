package com.workerp.common_lib.util;


import com.workerp.common_lib.exception.AppException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SecurityUtil {
    public static String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        if (userId == null || userId.isEmpty()) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Authentication is empty", "security-f-01");
        }
        return userId;
    }

    public static String getCompanyId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String companyId = request.getHeader("xxx-company-id");
        if (companyId == null || companyId.isEmpty()) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Header 'xxx-company-id' is missing or empty", "security-f-02");
        }
        return companyId;
    }
}
