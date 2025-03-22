package com.workerp.common_lib.enums.company_app_service;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ModuleRole {
    MANAGER("MANAGER"),
    USER("USER");

    private final String value;

    ModuleRole(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}