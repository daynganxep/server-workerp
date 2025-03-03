package com.workerp.common_lib.util;


public class Constant {
    public static String COMPANY_MODULE_ROLE_KEY(String companyId, String moduleCode, String userId) {
        return String.format("company-app:company-module-role:%s:%s:%s", companyId, moduleCode, userId);
    }
}
