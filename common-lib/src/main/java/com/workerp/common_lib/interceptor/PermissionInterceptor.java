package com.workerp.common_lib.interceptor;

import com.workerp.common_lib.annotation.CheckPermission;
import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import com.workerp.common_lib.enums.company_app_service.ModuleRole;
import com.workerp.common_lib.exception.AppException;
import com.workerp.common_lib.service.BaseRedisService;
import com.workerp.common_lib.util.AppConstant;
import com.workerp.common_lib.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PermissionInterceptor implements HandlerInterceptor {
    private final BaseRedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) return true;

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        CheckPermission checkPermission = handlerMethod.getMethodAnnotation(CheckPermission.class);
        if (checkPermission == null) return true;

        String companyId = request.getHeader("Xxx-Company-Id");
        String userId = SecurityUtil.getUserId();
        ModuleCode moduleCode = checkPermission.moduleCode();
        ModuleRole requiredRole = checkPermission.moduleRole();

        String key = AppConstant.COMPANY_MODULE_ROLE_KEY(companyId, moduleCode.toString(), userId);
        List<ModuleRole> moduleRoles = redisService.getList(key, ModuleRole.class);

        if (moduleRoles == null || (requiredRole != null && !moduleRoles.contains(requiredRole))) {
            throw new AppException(HttpStatus.FORBIDDEN, "Permission denied", "permission-01-01");
        }
        return true;
    }
}