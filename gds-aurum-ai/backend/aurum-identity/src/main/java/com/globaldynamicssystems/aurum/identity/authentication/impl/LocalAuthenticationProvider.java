package com.globaldynamicssystems.aurum.identity.authentication.impl;

import com.globaldynamicssystems.aurum.identity.authentication.AuthenticationProvider;
import com.globaldynamicssystems.aurum.identity.authentication.AuthenticationRequest;
import com.globaldynamicssystems.aurum.identity.authentication.AuthenticationResult;
import com.globaldynamicssystems.aurum.identity.authentication.AuthenticationService;

import org.springframework.stereotype.Component;

@Component
public class LocalAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationService authenticationService;

    public LocalAuthenticationProvider(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public String getCode() {
        return "LOCAL";
    }

    @Override
    public AuthenticationResult authenticate(AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }
}
