package com.workerp.common_lib.enums.project_app_service;

public enum ProjectMemberRole {
    LEADER("Leader"),
    MEMBER("Member");

    private final String displayName;

    ProjectMemberRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}