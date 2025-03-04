package com.workerp.common_lib.interceptor;

import com.workerp.common_lib.annotation.CheckPermission;
import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import com.workerp.common_lib.enums.company_app_service.ModuleRole;
import com.workerp.common_lib.exception.AppException;
import com.workerp.common_lib.service.BaseRedisService;
import com.workerp.common_lib.util.Constant;
import com.workerp.common_lib.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class PermissionInterceptor implements HandlerInterceptor {
    private final BaseRedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            CheckPermission checkPermission = handlerMethod.getMethodAnnotation(CheckPermission.class);

            if (checkPermission != null) {
                String companyId = request.getHeader("xxx-company-id");
                String userId = SecurityUtil.getUserId();
                ModuleCode moduleCode = checkPermission.moduleCode();
                ModuleRole requiredRole = checkPermission.moduleRole();

                String key = Constant.COMPANY_MODULE_ROLE_KEY(companyId, moduleCode.toString(), userId);
                String userRoleStr = (String) redisService.getRedisTemplate().opsForValue().get(key);

                if (userRoleStr == null) {
                    throw new AppException(HttpStatus.FORBIDDEN, "Permission denied: Role not found", "permission-01-01");
                }

                if (requiredRole == null || requiredRole.equals(ModuleRole.USER)) {
                    return true;
                }

                ModuleRole userRole = ModuleRole.valueOf(userRoleStr);
                if (!userRole.equals(requiredRole)) {
                    throw new AppException(HttpStatus.FORBIDDEN, "Permission denied: Role mismatch", "permission-01-02");
                }
            }
        }
        return true;
    }
}