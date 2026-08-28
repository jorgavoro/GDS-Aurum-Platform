package com.globaldynamicssystems.aurum.identity.service;

import com.globaldynamicssystems.aurum.identity.model.Role;

import java.util.List;

public interface RoleAssignmentService {
    void assignRole(Long userId, Long roleId);
    void removeRole(Long userId, Long roleId);
    List<Role> findUserRoles(Long userId);
}