package com.globaldynamicssystems.aurum.identity.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordPolicyValidatorTest {

    private PasswordPolicyValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PasswordPolicyValidator();
    }

    // Test 14: password mínima de 12 caracteres
    @Test
    void passwordWithExactMinimumLengthPasses() {
        assertDoesNotThrow(() -> validator.validate("123456789012"));
    }

    @Test
    void passwordShorterThanMinimumThrows() {
        assertThrows(InvalidPasswordException.class, () -> validator.validate("short"));
    }

    @Test
    void nullPasswordThrows() {
        assertThrows(InvalidPasswordException.class, () -> validator.validate(null));
    }

    @Test
    void passwordLongerThanMinimumPasses() {
        assertDoesNotThrow(() -> validator.validate("ThisIsAVeryLongAndSecurePassword!"));
    }
}
