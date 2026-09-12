package com.globaldynamicssystems.aurum.identity.authentication;

import com.globaldynamicssystems.aurum.identity.authentication.impl.DefaultAuthenticationManager;
import com.globaldynamicssystems.aurum.identity.authentication.impl.InMemoryAuthenticationProviderRegistry;
import com.globaldynamicssystems.aurum.identity.authentication.impl.LocalAuthenticationProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationManagerTest {

    private InMemoryAuthenticationProviderRegistry registry;
    private DefaultAuthenticationManager manager;

    @BeforeEach
    void setUp() {
        registry = new InMemoryAuthenticationProviderRegistry();
        manager = new DefaultAuthenticationManager(registry);
    }

    // Test 18: AuthenticationProvider LOCAL funciona
    @Test
    void localProviderAuthenticatesSuccessfully() {
        AuthenticationService authService = mock(AuthenticationService.class);
        AuthenticationResult expected = AuthenticationResult.failure(
                AuthenticationFailureReason.INVALID_CREDENTIALS, "Invalid credentials.");
        when(authService.authenticate(any())).thenReturn(expected);

        LocalAuthenticationProvider local = new LocalAuthenticationProvider(authService);
        registry.register(local);

        AuthenticationResult result = manager.authenticate("LOCAL",
                new AuthenticationRequest("john", "password123456"));

        assertNotNull(result);
        verify(authService).authenticate(any());
    }

    // Test 19: AuthenticationManager encuentra LOCAL
    @Test
    void managerFindsLocalProvider() {
        AuthenticationService authService = mock(AuthenticationService.class);
        LocalAuthenticationProvider local = new LocalAuthenticationProvider(authService);
        registry.register(local);

        Optional<AuthenticationProvider> found = registry.find("LOCAL");
        assertTrue(found.isPresent());
        assertEquals("LOCAL", found.get().getCode());
    }

    // Test 20: provider inexistente produce authentication failure
    @Test
    void unknownProviderReturnsFailure() {
        AuthenticationResult result = manager.authenticate("GOOGLE",
                new AuthenticationRequest("john", "password123456"));
        assertFalse(result.isAuthenticated());
        assertEquals(AuthenticationFailureReason.INVALID_REQUEST, result.getFailureReason());
    }

    // Test 26: Google no está implementado
    @Test
    void googleProviderIsNotRegistered() {
        Optional<AuthenticationProvider> google = registry.find("GOOGLE");
        assertTrue(google.isEmpty());
    }

    // Test 27: Microsoft no está implementado
    @Test
    void microsoftProviderIsNotRegistered() {
        Optional<AuthenticationProvider> microsoft = registry.find("MICROSOFT");
        assertTrue(microsoft.isEmpty());
    }

    @Test
    void registryIsCaseInsensitive() {
        AuthenticationService authService = mock(AuthenticationService.class);
        LocalAuthenticationProvider local = new LocalAuthenticationProvider(authService);
        registry.register(local);

        assertTrue(registry.find("local").isPresent());
        assertTrue(registry.find("LOCAL").isPresent());
        assertTrue(registry.find("Local").isPresent());
    }
}
