package com.globaldynamicssystems.aurum.identity.security.impl;

import com.globaldynamicssystems.aurum.identity.authorization.SecurityContext;
import com.globaldynamicssystems.aurum.identity.authorization.SecurityContextFactory;
import com.globaldynamicssystems.aurum.identity.exception.InvalidSecurityContextException;
import com.globaldynamicssystems.aurum.identity.exception.UserNotFoundException;
import com.globaldynamicssystems.aurum.identity.model.Permission;
import com.globaldynamicssystems.aurum.identity.model.Role;
import com.globaldynamicssystems.aurum.identity.model.RolePermission;
import com.globaldynamicssystems.aurum.identity.model.User;
import com.globaldynamicssystems.aurum.identity.model.UserStatus;
import com.globaldynamicssystems.aurum.identity.repository.RolePermissionRepository;
import com.globaldynamicssystems.aurum.identity.repository.UserRepository;
import com.globaldynamicssystems.aurum.identity.repository.UserRoleRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DefaultSecurityContextFactory implements SecurityContextFactory {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public DefaultSecurityContextFactory(UserRepository userRepository,
                                         UserRoleRepository userRoleRepository,
                                         RolePermissionRepository rolePermissionRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public SecurityContext create(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new InvalidSecurityContextException("User status is not ACTIVE. Current status: " + user.getStatus());
        }

        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();

        var userRoles = userRoleRepository.findByUserId(userId);
        for (var ur : userRoles) {
            Role role = ur.getRole();
            if (role != null && role.getCode() != null) {
                roles.add(role.getCode().trim().toUpperCase());

                List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleId(role.getId());
                for (RolePermission rp : rolePermissions) {
                    Permission perm = rp.getPermission();
                    if (perm != null && perm.getCode() != null && Boolean.TRUE.equals(perm.getEnabled())) {
                        permissions.add(perm.getCode().trim().toUpperCase());
                    }
                }
            }
        }

        Long tenantId = null;
        Long companyId = null;

        return new SecurityContext(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roles,
                permissions,
                tenantId,
                companyId
        );
    }
}