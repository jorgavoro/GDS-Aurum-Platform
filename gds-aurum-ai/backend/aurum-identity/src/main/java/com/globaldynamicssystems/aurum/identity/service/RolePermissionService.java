package com.globaldynamicssystems.aurum.identity.service;

import com.globaldynamicssystems.aurum.identity.model.Permission;

import java.util.List;
import java.util.UUID;

public interface RolePermissionService {
    void assignPermission(UUID roleId, UUID permissionId);
    void removePermission(UUID roleId, UUID permissionId);
    List<Permission> findRolePermissions(UUID roleId);
}