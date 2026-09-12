package com.globaldynamicssystems.aurum.identity.service.impl;

import com.globaldynamicssystems.aurum.identity.exception.RoleNotFoundException;
import com.globaldynamicssystems.aurum.identity.model.Role;
import com.globaldynamicssystems.aurum.identity.model.RoleStatus;
import com.globaldynamicssystems.aurum.identity.model.User;
import com.globaldynamicssystems.aurum.identity.model.UserRole;
import com.globaldynamicssystems.aurum.identity.repository.RoleRepository;
import com.globaldynamicssystems.aurum.identity.repository.UserRepository;
import com.globaldynamicssystems.aurum.identity.repository.UserRoleRepository;
import com.globaldynamicssystems.aurum.identity.service.RoleAssignmentService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DefaultRoleAssignmentService implements RoleAssignmentService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public DefaultRoleAssignmentService(UserRepository userRepository,
                                        RoleRepository roleRepository,
                                        UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    @Transactional
    public void assignRole(UUID userId, UUID roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id " + userId + " not found."));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role with id " + roleId + " not found."));

        if (role.getStatus() != RoleStatus.ACTIVE) {
            throw new IllegalStateException("Cannot assign non-active role.");
        }

        boolean exists = userRoleRepository.existsByUserIdAndRoleId(user.getId(), roleId);
        if (!exists) {
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            userRoleRepository.save(userRole);
        }
    }

    @Override
    @Transactional
    public void removeRole(UUID userId, UUID roleId) {
        userRepository.findById(userId)
                .ifPresent(user -> {
                    if (userRoleRepository.existsByUserIdAndRoleId(user.getId(), roleId)) {
                        userRoleRepository.deleteByUserIdAndRoleId(user.getId(), roleId);
                    }
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findUserRoles(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id " + userId + " not found."));
        return userRoleRepository.findByUserId(user.getId()).stream()
                .map(UserRole::getRole)
                .collect(Collectors.toList());
    }
}
