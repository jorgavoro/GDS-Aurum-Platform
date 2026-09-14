package com.globaldynamicssystems.aurum.identity.authentication.impl;

import com.globaldynamicssystems.aurum.identity.authentication.AuthenticationFailureReason;
import com.globaldynamicssystems.aurum.identity.authentication.AuthenticationManager;
import com.globaldynamicssystems.aurum.identity.authentication.AuthenticationProvider;
import com.globaldynamicssystems.aurum.identity.authentication.AuthenticationProviderRegistry;
import com.globaldynamicssystems.aurum.identity.authentication.AuthenticationRequest;
import com.globaldynamicssystems.aurum.identity.authentication.AuthenticationResult;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultAuthenticationManager implements AuthenticationManager {

    private final AuthenticationProviderRegistry registry;

    public DefaultAuthenticationManager(AuthenticationProviderRegistry registry) {
        this.registry = registry;
    }

    @Override
    public AuthenticationResult authenticate(String provider, AuthenticationRequest request) {
        if (provider == null || provider.trim().isEmpty()) {
            return AuthenticationResult.failure(AuthenticationFailureReason.INVALID_REQUEST,
                    "Authentication provider must be specified.");
        }

        Optional<AuthenticationProvider> found = registry.find(provider);
        if (found.isEmpty()) {
            return AuthenticationResult.failure(AuthenticationFailureReason.INVALID_REQUEST,
                    "Authentication provider not found: " + provider);
        }

        return found.get().authenticate(request);
    }
}
