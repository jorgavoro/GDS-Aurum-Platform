package com.globaldynamicssystems.aurum.identity.service.impl;

import com.globaldynamicssystems.aurum.identity.exception.PermissionNotFoundException;
import com.globaldynamicssystems.aurum.identity.exception.RoleNotFoundException;
import com.globaldynamicssystems.aurum.identity.model.Permission;
import com.globaldynamicssystems.aurum.identity.model.Role;
import com.globaldynamicssystems.aurum.identity.model.RolePermission;
import com.globaldynamicssystems.aurum.identity.model.RoleStatus;
import com.globaldynamicssystems.aurum.identity.repository.PermissionRepository;
import com.globaldynamicssystems.aurum.identity.repository.RolePermissionRepository;
import com.globaldynamicssystems.aurum.identity.repository.RoleRepository;
import com.globaldynamicssystems.aurum.identity.service.RolePermissionService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DefaultRolePermissionService implements RolePermissionService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public DefaultRolePermissionService(RoleRepository roleRepository,
                                        PermissionRepository permissionRepository,
                                        RolePermissionRepository rolePermissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    @Transactional
    public void assignPermission(UUID roleId, UUID permissionId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role with id " + roleId + " not found."));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new PermissionNotFoundException("Permission with id " + permissionId + " not found."));

        if (role.getStatus() != RoleStatus.ACTIVE) {
            throw new IllegalStateException("Cannot assign permission to a non-active role.");
        }

        if (Boolean.FALSE.equals(permission.getEnabled())) {
            throw new IllegalStateException("Cannot assign a disabled permission.");
        }

        boolean exists = rolePermissionRepository.existsByRoleIdAndPermissionId(roleId, permissionId);
        if (!exists) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRole(role);
            rolePermission.setPermission(permission);
            rolePermissionRepository.save(rolePermission);
        }
    }

    @Override
    @Transactional
    public void removePermission(UUID roleId, UUID permissionId) {
        boolean exists = rolePermissionRepository.existsByRoleIdAndPermissionId(roleId, permissionId);
        if (exists) {
            rolePermissionRepository.deleteByRoleIdAndPermissionId(roleId, permissionId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Permission> findRolePermissions(UUID roleId) {
        List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleId(roleId);
        return rolePermissions.stream()
                .map(RolePermission::getPermission)
                .collect(Collectors.toList());
    }
}
