package com.globaldynamicssystems.aurum.identity.authorization;

import java.util.Set;
import java.util.UUID;

public class SecurityContext {

    private final UUID userId;
    private final String username;
    private final String email;
    private final Set<String> roles;
    private final Set<String> permissions;
    private final UUID tenantId;
    private final UUID companyId;

    public SecurityContext(UUID userId, String username, String email,
                           Set<String> roles, Set<String> permissions,
                           UUID tenantId, UUID companyId) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.roles = roles != null ? Set.copyOf(roles) : Set.of();
        this.permissions = permissions != null ? Set.copyOf(permissions) : Set.of();
        this.tenantId = tenantId;
        this.companyId = companyId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public boolean hasRole(String roleCode) {
        if (roleCode == null) return false;
        return roles.contains(roleCode.trim().toUpperCase());
    }

    public boolean hasPermission(String permissionCode) {
        if (permissionCode == null) return false;
        return permissions.contains(permissionCode.trim().toUpperCase());
    }
}