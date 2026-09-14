package com.globaldynamicssystems.aurum.identity.security;

import java.util.Optional;

import com.globaldynamicssystems.aurum.identity.authorization.SecurityContext;

public interface SecurityContextHolder {
    void setContext(SecurityContext context);
    Optional<SecurityContext> getContext();
    void clear();
}