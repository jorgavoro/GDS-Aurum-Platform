package com.globaldynamicssystems.aurum.identity.service;

import com.globaldynamicssystems.aurum.identity.authorization.AuthorizationResult;
import com.globaldynamicssystems.aurum.identity.model.PermissionAction;

public interface ApplicationAccessService {
    boolean canAccess(String capability, String resource, PermissionAction action);
    AuthorizationResult authorize(String capability, String resource, PermissionAction action);
}
