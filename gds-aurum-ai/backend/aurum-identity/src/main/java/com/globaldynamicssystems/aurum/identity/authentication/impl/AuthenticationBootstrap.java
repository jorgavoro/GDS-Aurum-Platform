package com.globaldynamicssystems.aurum.identity.authentication.impl;

import com.globaldynamicssystems.aurum.identity.authentication.AuthenticationProvider;
import com.globaldynamicssystems.aurum.identity.authentication.AuthenticationProviderRegistry;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationBootstrap {

    private final AuthenticationProviderRegistry registry;
    private final AuthenticationProvider localAuthenticationProvider;

    public AuthenticationBootstrap(AuthenticationProviderRegistry registry,
                                   AuthenticationProvider localAuthenticationProvider) {
        this.registry = registry;
        this.localAuthenticationProvider = localAuthenticationProvider;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void onApplicationReady() {
        registry.register(localAuthenticationProvider);
    }
}
