package com.globaldynamicssystems.aurum.identity.authorization;

import com.globaldynamicssystems.aurum.identity.model.Permission;

public interface AuthorizationPolicy {
    boolean supports(AuthorizationRequest request, Permission permission);
}