package com.globaldynamicssystems.aurum.identity.authentication;

public interface PasswordHasher {

    String hash(String rawPassword);

    boolean matches(String rawPassword, String passwordHash);
}
