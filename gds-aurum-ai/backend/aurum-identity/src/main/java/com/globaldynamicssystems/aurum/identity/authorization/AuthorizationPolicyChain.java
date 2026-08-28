package com.globaldynamicssystems.aurum.identity.authorization;

import com.globaldynamicssystems.aurum.identity.model.Permission;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorizationPolicyChain {

    private final List<AuthorizationPolicy> policies;

    public AuthorizationPolicyChain(List<AuthorizationPolicy> policies) {
        this.policies = policies;
    }

    public boolean evaluate(AuthorizationRequest request, Permission permission) {
        for (AuthorizationPolicy policy : policies) {
            if (policy.supports(request, permission)) {
                return true;
            }
        }
        return false;
    }
}