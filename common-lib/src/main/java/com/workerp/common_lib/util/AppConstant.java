package com.workerp.common_lib.util;


public class AppConstant {
    public static String COMPANY_MODULE_ROLE_KEY(String companyId, String moduleCode, String userId) {
        return String.format("company-app:company-module-role:%s:%s:%s", companyId, moduleCode, userId);
    }
    public static final String EMAIL_QUEUE = "email.queue";
    public static final String EMAIL_EXCHANGE = "email.exchange";
    public static final String EMAIL_ROUTING_KEY = "email.routing.key";

    public static final String ADD_EMPLOYEE_QUEUE = "add-employee.queue";
    public static final String ADD_EMPLOYEE_EXCHANGE = "add-employee.exchange";
    public static final String ADD_EMPLOYEE_ROUTING_KEY = "add-employee.routing.key";
}
