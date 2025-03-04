package com.workerp.common_lib.annotation;

import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import com.workerp.common_lib.enums.company_app_service.ModuleRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPermission {
    ModuleCode moduleCode();

    ModuleRole moduleRole();
}