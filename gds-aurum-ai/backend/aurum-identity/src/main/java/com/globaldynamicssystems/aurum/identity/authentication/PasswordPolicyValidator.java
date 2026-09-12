package com.globaldynamicssystems.aurum.identity.authentication;

import org.springframework.stereotype.Component;

@Component
public class PasswordPolicyValidator {

    private final PasswordPolicy policy;

    public PasswordPolicyValidator() {
        this.policy = new PasswordPolicy();
    }

    public PasswordPolicyValidator(PasswordPolicy policy) {
        this.policy = policy;
    }

    public void validate(String password) {
        if (password == null || password.length() < policy.getMinimumLength()) {
            throw new InvalidPasswordException(
                    "Password must be at least " + policy.getMinimumLength() + " characters long.");
        }
    }
}
