package com.globaldynamicssystems.aurum.identity.authentication;

public interface AuthenticationProvider {

    String getCode();

    AuthenticationResult authenticate(AuthenticationRequest request);
}
