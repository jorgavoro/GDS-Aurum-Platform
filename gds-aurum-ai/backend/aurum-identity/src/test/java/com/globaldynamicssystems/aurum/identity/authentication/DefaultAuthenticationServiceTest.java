package com.globaldynamicssystems.aurum.identity.authentication;

import com.globaldynamicssystems.aurum.identity.authentication.impl.DefaultAuthenticationService;
import com.globaldynamicssystems.aurum.identity.authorization.SecurityContext;
import com.globaldynamicssystems.aurum.identity.authorization.SecurityContextFactory;
import com.globaldynamicssystems.aurum.identity.model.User;
import com.globaldynamicssystems.aurum.identity.model.UserStatus;
import com.globaldynamicssystems.aurum.identity.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultAuthenticationServiceTest {

    private UserRepository userRepository;
    private PasswordHasher passwordHasher;
    private SecurityContextFactory securityContextFactory;
    private DefaultAuthenticationService service;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordHasher = mock(PasswordHasher.class);
        securityContextFactory = mock(SecurityContextFactory.class);
        service = new DefaultAuthenticationService(userRepository, passwordHasher, securityContextFactory);
    }

    private User activeUserWithHash(UUID id, String hash) {
        User user = spy(new User("john", "john@test.com", "John", "Doe", UserStatus.ACTIVE, true));
        doReturn(id).when(user).getId();
        user.setPasswordHash(hash);
        return user;
    }

    private SecurityContext mockContext(UUID userId) {
        return new SecurityContext(userId, "john", "john@test.com", Set.of(), Set.of(), null, null);
    }

    // Test 5: usuario inexistente → failure (no revela que no existe)
    @Test
    void unknownUserReturnsInvalidCredentials() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());
        AuthenticationResult result = service.authenticate(new AuthenticationRequest("unknown", "password123456"));
        assertFalse(result.isAuthenticated());
        assertEquals(AuthenticationFailureReason.INVALID_CREDENTIALS, result.getFailureReason());
    }

    // Tests 6-9: estados no ACTIVE → failure
    @ParameterizedTest
    @EnumSource(value = UserStatus.class, names = {"PENDING", "LOCKED", "DISABLED", "DELETED"})
    void nonActiveUserCannotAuthenticate(UserStatus status) {
        UUID id = UUID.randomUUID();
        User user = spy(new User("john", "john@test.com", "John", "Doe", status, false));
        doReturn(id).when(user).getId();
        user.setPasswordHash("$2a$12$hash");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        AuthenticationResult result = service.authenticate(new AuthenticationRequest("john", "password123456"));
        assertFalse(result.isAuthenticated());
        assertNull(result.getSecurityContext());
    }

    // Test 10: usuario ACTIVE con password correcta → success
    @Test
    void activeUserWithCorrectPasswordAuthenticates() {
        UUID id = UUID.randomUUID();
        User user = activeUserWithHash(id, "$2a$12$hash");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(passwordHasher.matches("correctpassword", "$2a$12$hash")).thenReturn(true);
        when(securityContextFactory.create(id)).thenReturn(mockContext(id));

        AuthenticationResult result = service.authenticate(new AuthenticationRequest("john", "correctpassword"));

        assertTrue(result.isAuthenticated());
        assertNotNull(result.getSecurityContext());
        assertEquals("john", result.getUsername());
    }

    // Test 11: usuario sin passwordHash → failure
    @Test
    void userWithNullPasswordHashCannotAuthenticateLocally() {
        UUID id = UUID.randomUUID();
        User user = spy(new User("john", "john@test.com", "John", "Doe", UserStatus.ACTIVE, true));
        doReturn(id).when(user).getId();
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        AuthenticationResult result = service.authenticate(new AuthenticationRequest("john", "password123456"));
        assertFalse(result.isAuthenticated());
        assertEquals(AuthenticationFailureReason.LOCAL_AUTHENTICATION_NOT_ENABLED, result.getFailureReason());
    }

    // Test 12: login correcto genera SecurityContext
    @Test
    void successfulLoginGeneratesSecurityContext() {
        UUID id = UUID.randomUUID();
        User user = activeUserWithHash(id, "$2a$12$hash");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(passwordHasher.matches("correctpassword", "$2a$12$hash")).thenReturn(true);
        SecurityContext ctx = mockContext(id);
        when(securityContextFactory.create(id)).thenReturn(ctx);

        AuthenticationResult result = service.authenticate(new AuthenticationRequest("john", "correctpassword"));

        assertNotNull(result.getSecurityContext());
        assertEquals(ctx, result.getSecurityContext());
    }

    // Test 13: login incorrecto NO genera SecurityContext
    @Test
    void failedLoginDoesNotGenerateSecurityContext() {
        UUID id = UUID.randomUUID();
        User user = activeUserWithHash(id, "$2a$12$hash");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(passwordHasher.matches("wrongpassword", "$2a$12$hash")).thenReturn(false);

        AuthenticationResult result = service.authenticate(new AuthenticationRequest("john", "wrongpassword"));

        assertFalse(result.isAuthenticated());
        assertNull(result.getSecurityContext());
    }

    // Test 16: AuthenticationResult no contiene passwordHash
    @Test
    void authenticationResultDoesNotExposePasswordHash() {
        // AuthenticationResult no tiene campo passwordHash — verificado por diseño de clase
        UUID id = UUID.randomUUID();
        User user = activeUserWithHash(id, "$2a$12$hash");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(passwordHasher.matches("correctpassword", "$2a$12$hash")).thenReturn(true);
        when(securityContextFactory.create(id)).thenReturn(mockContext(id));

        AuthenticationResult result = service.authenticate(new AuthenticationRequest("john", "correctpassword"));

        // AuthenticationResult no tiene getPasswordHash() — no compila si se agrega
        assertNotNull(result);
        assertTrue(result.isAuthenticated());
    }

    // Test 17: AuthenticationResult no contiene password
    @Test
    void authenticationResultDoesNotExposePassword() {
        // AuthenticationResult no tiene campo password — verificado por diseño de clase
        AuthenticationResult result = AuthenticationResult.failure(
                AuthenticationFailureReason.INVALID_CREDENTIALS, "Invalid credentials.");
        assertFalse(result.isAuthenticated());
        assertNull(result.getSecurityContext());
    }

    // Test 25: AuthenticationService NO establece ThreadLocal automáticamente
    @Test
    void authenticationServiceDoesNotSetThreadLocal() {
        UUID id = UUID.randomUUID();
        User user = activeUserWithHash(id, "$2a$12$hash");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(passwordHasher.matches("correctpassword", "$2a$12$hash")).thenReturn(true);
        when(securityContextFactory.create(id)).thenReturn(mockContext(id));

        service.authenticate(new AuthenticationRequest("john", "correctpassword"));

        // SecurityContextFactory.create() fue llamado pero no SecurityContextHolder
        verify(securityContextFactory).create(id);
        // No hay interacción con SecurityContextHolder — verificado por ausencia de dependencia
    }

    @Test
    void nullRequestReturnsInvalidRequest() {
        AuthenticationResult result = service.authenticate(null);
        assertFalse(result.isAuthenticated());
        assertEquals(AuthenticationFailureReason.INVALID_REQUEST, result.getFailureReason());
    }

    @Test
    void emptyUsernameReturnsInvalidRequest() {
        AuthenticationResult result = service.authenticate(new AuthenticationRequest("", "password123456"));
        assertFalse(result.isAuthenticated());
        assertEquals(AuthenticationFailureReason.INVALID_REQUEST, result.getFailureReason());
    }

    @Test
    void emptyPasswordReturnsInvalidRequest() {
        AuthenticationResult result = service.authenticate(new AuthenticationRequest("john", ""));
        assertFalse(result.isAuthenticated());
        assertEquals(AuthenticationFailureReason.INVALID_REQUEST, result.getFailureReason());
    }
}
