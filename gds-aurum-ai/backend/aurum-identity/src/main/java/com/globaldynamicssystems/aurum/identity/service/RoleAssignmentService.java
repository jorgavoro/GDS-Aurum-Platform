package com.globaldynamicssystems.aurum.identity.service;

import com.globaldynamicssystems.aurum.identity.model.Role;

import java.util.List;
import java.util.UUID;

public interface RoleAssignmentService {
    void assignRole(UUID userId, UUID roleId);
    void removeRole(UUID userId, UUID roleId);
    List<Role> findUserRoles(UUID userId);
}