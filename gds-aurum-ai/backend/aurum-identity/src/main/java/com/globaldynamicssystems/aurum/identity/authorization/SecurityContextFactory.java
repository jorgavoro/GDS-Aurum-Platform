package com.globaldynamicssystems.aurum.identity.authorization;

public interface SecurityContextFactory {
    SecurityContext create(Long userId);
}