package com.rest.api.article.security;

public enum ApplicationUserPermission {
    ROLE_READER("user:read"),
    ROLE_WRITER("user:write"),
    ROLE_USER("admin:read"),
    ROLE_ADMIN("admin:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}