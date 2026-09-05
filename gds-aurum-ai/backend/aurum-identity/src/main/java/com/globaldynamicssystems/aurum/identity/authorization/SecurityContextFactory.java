package com.globaldynamicssystems.aurum.identity.authorization;

import java.util.UUID;

public interface SecurityContextFactory {
    SecurityContext create(UUID userId);
}