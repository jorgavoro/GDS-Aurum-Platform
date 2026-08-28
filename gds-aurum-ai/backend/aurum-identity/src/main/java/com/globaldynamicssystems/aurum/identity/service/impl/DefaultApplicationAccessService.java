package com.globaldynamicssystems.aurum.identity.service.impl;

import com.globaldynamicssystems.aurum.identity.authorization.AuthorizationDecision;
import com.globaldynamicssystems.aurum.identity.authorization.AuthorizationRequest;
import com.globaldynamicssystems.aurum.identity.authorization.AuthorizationResult;
import com.globaldynamicssystems.aurum.identity.authorization.SecurityContext;
import com.globaldynamicssystems.aurum.identity.service.AuthorizationService;
import com.globaldynamicssystems.aurum.identity.model.PermissionAction;
import com.globaldynamicssystems.aurum.identity.model.PermissionScopeType;
import com.globaldynamicssystems.aurum.identity.service.ApplicationAccessService;
import com.globaldynamicssystems.aurum.identity.service.AuthorizationService;
import com.globaldynamicssystems.aurum.identity.service.SecurityContextService;

import org.springframework.stereotype.Service;

@Service
public class DefaultApplicationAccessService implements ApplicationAccessService {

    private final SecurityContextService securityContextService;
    private final AuthorizationService authorizationService;

    public DefaultApplicationAccessService(SecurityContextService securityContextService,
                                           AuthorizationService authorizationService) {
        this.securityContextService = securityContextService;
        this.authorizationService = authorizationService;
    }

    @Override
    public boolean canAccess(String capability, String resource, PermissionAction action) {
        AuthorizationResult result = authorize(capability, resource, action);
        return result != null && result.getDecision() == AuthorizationDecision.ALLOW;
    }

    @Override
    public AuthorizationResult authorize(String capability, String resource, PermissionAction action) {
        SecurityContext context = securityContextService.requireContext();
        
        AuthorizationRequest request = new AuthorizationRequest(
                context.getUserId(),
                capability,
                resource,
                action,
                PermissionScopeType.COMPANY,
                null
        );

        return authorizationService.authorize(request);
    }
}