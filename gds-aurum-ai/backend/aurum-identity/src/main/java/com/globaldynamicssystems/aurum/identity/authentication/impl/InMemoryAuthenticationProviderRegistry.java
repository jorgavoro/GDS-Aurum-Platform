package com.globaldynamicssystems.aurum.identity.authentication.impl;

import com.globaldynamicssystems.aurum.identity.authentication.AuthenticationProvider;
import com.globaldynamicssystems.aurum.identity.authentication.AuthenticationProviderRegistry;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryAuthenticationProviderRegistry implements AuthenticationProviderRegistry {

    private final ConcurrentHashMap<String, AuthenticationProvider> providers = new ConcurrentHashMap<>();

    @Override
    public void register(AuthenticationProvider provider) {
        if (provider == null || provider.getCode() == null) {
            throw new IllegalArgumentException("AuthenticationProvider and its code must not be null.");
        }
        providers.put(provider.getCode().toUpperCase(), provider);
    }

    @Override
    public Optional<AuthenticationProvider> find(String code) {
        if (code == null) return Optional.empty();
        return Optional.ofNullable(providers.get(code.toUpperCase()));
    }

    @Override
    public List<AuthenticationProvider> findAll() {
        return new ArrayList<>(providers.values());
    }
}
