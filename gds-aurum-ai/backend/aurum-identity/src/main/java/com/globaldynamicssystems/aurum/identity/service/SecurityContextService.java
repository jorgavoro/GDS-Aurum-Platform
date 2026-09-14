package com.globaldynamicssystems.aurum.identity.service;

import java.util.Optional;

import com.globaldynamicssystems.aurum.identity.authorization.SecurityContext;

public interface SecurityContextService {
    void setContext(SecurityContext context);
    Optional<SecurityContext> getContext();
    SecurityContext requireContext();
    void clearContext();
}