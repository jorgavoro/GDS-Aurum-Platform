package com.globaldynamicssystems.aurum.identity.service.impl;

import com.globaldynamicssystems.aurum.identity.authorization.AuthorizationDecision;
import com.globaldynamicssystems.aurum.identity.authorization.AuthorizationPolicyChain;
import com.globaldynamicssystems.aurum.identity.authorization.AuthorizationReason;
import com.globaldynamicssystems.aurum.identity.authorization.AuthorizationRequest;
import com.globaldynamicssystems.aurum.identity.authorization.AuthorizationResult;
import com.globaldynamicssystems.aurum.identity.model.Permission;
import com.globaldynamicssystems.aurum.identity.model.Role;
import com.globaldynamicssystems.aurum.identity.model.RolePermission;
import com.globaldynamicssystems.aurum.identity.model.User;
import com.globaldynamicssystems.aurum.identity.model.UserRole;
import com.globaldynamicssystems.aurum.identity.model.UserStatus;
import com.globaldynamicssystems.aurum.identity.repository.PermissionRepository;
import com.globaldynamicssystems.aurum.identity.repository.RolePermissionRepository;
import com.globaldynamicssystems.aurum.identity.repository.RoleRepository;
import com.globaldynamicssystems.aurum.identity.repository.UserRepository;
import com.globaldynamicssystems.aurum.identity.repository.UserRoleRepository;
import com.globaldynamicssystems.aurum.identity.service.AuthorizationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultAuthorizationService implements AuthorizationService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;
    private final AuthorizationPolicyChain policyChain;

    public DefaultAuthorizationService(UserRepository userRepository,
                                       UserRoleRepository userRoleRepository,
                                       RoleRepository roleRepository,
                                       RolePermissionRepository rolePermissionRepository,
                                       PermissionRepository permissionRepository,
                                       AuthorizationPolicyChain policyChain) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
        this.policyChain = policyChain;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorizationResult authorize(AuthorizationRequest request) {
        if (request == null || request.getUserId() == null || 
            request.getCapability() == null || request.getCapability().trim().isEmpty() ||
            request.getResource() == null || request.getResource().trim().isEmpty() ||
            request.getAction() == null || request.getScopeType() == null) {
            return new AuthorizationResult(AuthorizationDecision.DENY, AuthorizationReason.INVALID_REQUEST, 
                    "Invalid authorization request parameters.", null, null);
        }

        Optional<User> userOpt = userRepository.findById(request.getUserId());
        if (userOpt.isEmpty()) {
            return new AuthorizationResult(AuthorizationDecision.DENY, AuthorizationReason.USER_NOT_FOUND, 
                    "User not found.", request.getUserId(), null);
        }

        User user = userOpt.get();
        if (user.getStatus() == UserStatus.DISABLED || user.getStatus() == UserStatus.DELETED) {
            return new AuthorizationResult(AuthorizationDecision.DENY, AuthorizationReason.USER_DISABLED, 
                    "User is disabled or deleted.", request.getUserId(), null);
        }
        if (user.getStatus() == UserStatus.LOCKED) {
            return new AuthorizationResult(AuthorizationDecision.DENY, AuthorizationReason.USER_LOCKED, 
                    "User is locked.", request.getUserId(), null);
        }

        List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
        if (userRoles.isEmpty()) {
            return new AuthorizationResult(AuthorizationDecision.DENY, AuthorizationReason.NO_ROLE, 
                    "User has no assigned roles.", request.getUserId(), null);
        }

        String normCapability = request.getCapability().trim().toUpperCase();
        String normResource = request.getResource().trim().toUpperCase();

        boolean foundMatchingPermission = false;
        boolean scopeMismatchFound = false;
        boolean permissionDisabledFound = false;
        String matchedCode = null;

        for (UserRole userRole : userRoles) {
            List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleId(userRole.getRole().getId());
            for (RolePermission rp : rolePermissions) {
                Permission perm = rp.getPermission();
                
                if (perm.getCapability().equals(normCapability) &&
                    perm.getResource().equals(normResource) &&
                    perm.getAction().equals(request.getAction())) {

                    if (Boolean.FALSE.equals(perm.getEnabled())) {
                        permissionDisabledFound = true;
                        continue;
                    }

                    if (policyChain.evaluate(request, perm)) {
                        foundMatchingPermission = true;
                        matchedCode = perm.getCode();
                        break;
                    } else {
                        scopeMismatchFound = true;
                    }
                }
            }
            if (foundMatchingPermission) {
                break;
            }
        }

        if (foundMatchingPermission) {
            return new AuthorizationResult(AuthorizationDecision.ALLOW, AuthorizationReason.AUTHORIZED, 
                    "Access authorized.", request.getUserId(), matchedCode);
        }

        if (scopeMismatchFound) {
            return new AuthorizationResult(AuthorizationDecision.DENY, AuthorizationReason.SCOPE_MISMATCH, 
                    "Scope mismatch for the requested permission.", request.getUserId(), null);
        }

        if (permissionDisabledFound) {
            return new AuthorizationResult(AuthorizationDecision.DENY, AuthorizationReason.PERMISSION_DISABLED, 
                    "Matching permission is disabled.", request.getUserId(), null);
        }

        return new AuthorizationResult(AuthorizationDecision.DENY, AuthorizationReason.NO_PERMISSION, 
                "User does not have permission.", request.getUserId(), null);
    }
}
