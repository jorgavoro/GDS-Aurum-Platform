package com.globaldynamicssystems.aurum.identity.authentication;

import com.globaldynamicssystems.aurum.identity.authentication.impl.BCryptPasswordHasher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BCryptPasswordHasherTest {

    private BCryptPasswordHasher hasher;

    @BeforeEach
    void setUp() {
        hasher = new BCryptPasswordHasher();
    }

    // Test 1: hash generado correctamente
    @Test
    void hashProducesNonNullResult() {
        String hash = hasher.hash("SecurePassword123!");
        assertNotNull(hash);
        assertFalse(hash.isBlank());
    }

    // Test 2: hash nunca coincide con texto plano
    @Test
    void hashNeverEqualsRawPassword() {
        String raw = "SecurePassword123!";
        String hash = hasher.hash(raw);
        assertNotEquals(raw, hash);
    }

    // Test 3: password correcta produce matches = true
    @Test
    void matchesReturnsTrueForCorrectPassword() {
        String raw = "SecurePassword123!";
        String hash = hasher.hash(raw);
        assertTrue(hasher.matches(raw, hash));
    }

    // Test 4: password incorrecta produce matches = false
    @Test
    void matchesReturnsFalseForWrongPassword() {
        String hash = hasher.hash("SecurePassword123!");
        assertFalse(hasher.matches("WrongPassword999!", hash));
    }

    // Test 15: dos hashes del mismo password son distintos (salt aleatorio)
    @Test
    void twoHashesOfSamePasswordAreDifferent() {
        String raw = "SecurePassword123!";
        String hash1 = hasher.hash(raw);
        String hash2 = hasher.hash(raw);
        assertNotEquals(hash1, hash2);
        assertTrue(hasher.matches(raw, hash1));
        assertTrue(hasher.matches(raw, hash2));
    }
}
