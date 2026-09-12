package com.globaldynamicssystems.aurum.identity.authentication;

public interface AuthenticationManager {

    AuthenticationResult authenticate(String provider, AuthenticationRequest request);
}
