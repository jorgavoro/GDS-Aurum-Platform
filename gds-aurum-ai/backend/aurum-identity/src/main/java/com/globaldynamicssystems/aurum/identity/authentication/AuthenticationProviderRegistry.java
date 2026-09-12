package com.globaldynamicssystems.aurum.identity.authentication;

import java.util.List;
import java.util.Optional;

public interface AuthenticationProviderRegistry {

    void register(AuthenticationProvider provider);

    Optional<AuthenticationProvider> find(String code);

    List<AuthenticationProvider> findAll();
}
