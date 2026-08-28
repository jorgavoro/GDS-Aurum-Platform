package com.globaldynamicssystems.aurum.identity.authorization;

import com.globaldynamicssystems.aurum.identity.model.Permission;
import com.globaldynamicssystems.aurum.identity.model.PermissionScopeType;
import org.springframework.stereotype.Component;

@Component
public class CompanyScopeAuthorizationPolicy implements AuthorizationPolicy {

    @Override
    public boolean supports(AuthorizationRequest request, Permission permission) {
        return request.getScopeType() == PermissionScopeType.COMPANY 
                && permission.getScopeType() == PermissionScopeType.COMPANY;
    }
}