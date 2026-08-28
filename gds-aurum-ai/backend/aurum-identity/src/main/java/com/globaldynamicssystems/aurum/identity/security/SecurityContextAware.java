package com.globaldynamicssystems.aurum.identity.security;

import com.globaldynamicssystems.aurum.identity.authorization.SecurityContext;

public interface SecurityContextAware {
    SecurityContext getSecurityContext();
}