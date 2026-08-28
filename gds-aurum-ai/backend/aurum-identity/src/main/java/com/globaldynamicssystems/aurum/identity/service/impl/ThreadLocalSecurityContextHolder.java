package com.globaldynamicssystems.aurum.identity.service.impl;

import org.springframework.stereotype.Component;

import com.globaldynamicssystems.aurum.identity.authorization.SecurityContext;
import com.globaldynamicssystems.aurum.identity.security.SecurityContextHolder;

import java.util.Optional;

@Component
public class ThreadLocalSecurityContextHolder implements SecurityContextHolder {

    private static final ThreadLocal<SecurityContext> contextHolder = new ThreadLocal<>();

    @Override
    public void setContext(SecurityContext context) {
        if (context == null) {
            clear();
        } else {
            contextHolder.set(context);
        }
    }

    @Override
    public Optional<SecurityContext> getContext() {
        return Optional.ofNullable(contextHolder.get());
    }

    @Override
    public void clear() {
        contextHolder.remove();
    }
}
