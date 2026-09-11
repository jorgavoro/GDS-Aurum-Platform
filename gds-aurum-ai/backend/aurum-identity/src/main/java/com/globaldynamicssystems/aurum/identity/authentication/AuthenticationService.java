package com.globaldynamicssystems.aurum.identity.authentication;

public interface AuthenticationService {

    AuthenticationResult authenticate(AuthenticationRequest request);
}
