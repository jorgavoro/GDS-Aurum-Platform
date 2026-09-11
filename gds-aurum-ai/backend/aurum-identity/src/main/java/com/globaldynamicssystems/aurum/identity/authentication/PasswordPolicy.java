package com.globaldynamicssystems.aurum.identity.authentication;

public class PasswordPolicy {

    private final int minimumLength;

    public PasswordPolicy() {
        this.minimumLength = 12;
    }

    public PasswordPolicy(int minimumLength) {
        this.minimumLength = minimumLength;
    }

    public int getMinimumLength() {
        return minimumLength;
    }
}
