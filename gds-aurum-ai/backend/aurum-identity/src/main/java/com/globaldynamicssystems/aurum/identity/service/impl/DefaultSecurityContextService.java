package com.globaldynamicssystems.aurum.identity.service.impl;

import org.springframework.stereotype.Service;

import com.globaldynamicssystems.aurum.identity.authorization.SecurityContext;
import com.globaldynamicssystems.aurum.identity.exception.SecurityContextNotAvailableException;
import com.globaldynamicssystems.aurum.identity.security.SecurityContextHolder;
import com.globaldynamicssystems.aurum.identity.service.SecurityContextService;

import java.util.Optional;

@Service
public class DefaultSecurityContextService implements SecurityContextService {

    private final SecurityContextHolder securityContextHolder;

    public DefaultSecurityContextService(SecurityContextHolder securityContextHolder) {
        this.securityContextHolder = securityContextHolder;
    }

    @Override
    public void setContext(SecurityContext context) {
        securityContextHolder.setContext(context);
    }

    @Override
    public Optional<SecurityContext> getContext() {
        return securityContextHolder.getContext();
    }

    @Override
    public SecurityContext requireContext() {
        return getContext().orElseThrow(SecurityContextNotAvailableException::new);
    }

    @Override
    public void clearContext() {
        securityContextHolder.clear();
    }
}