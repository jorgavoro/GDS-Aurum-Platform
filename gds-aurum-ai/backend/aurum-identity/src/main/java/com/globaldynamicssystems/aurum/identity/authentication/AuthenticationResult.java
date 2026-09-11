package com.globaldynamicssystems.aurum.identity.authentication;

import com.globaldynamicssystems.aurum.identity.authorization.SecurityContext;

import java.util.UUID;

public class AuthenticationResult {

    private final boolean authenticated;
    private final UUID userId;
    private final String username;
    private final String message;
    private final AuthenticationFailureReason failureReason;
    private final SecurityContext securityContext;

    private AuthenticationResult(boolean authenticated, UUID userId, String username,
                                  String message, AuthenticationFailureReason failureReason,
                                  SecurityContext securityContext) {
        this.authenticated = authenticated;
        this.userId = userId;
        this.username = username;
        this.message = message;
        this.failureReason = failureReason;
        this.securityContext = securityContext;
    }

    public static AuthenticationResult success(UUID userId, String username, SecurityContext securityContext) {
        return new AuthenticationResult(true, userId, username, "Authentication successful.", null, securityContext);
    }

    public static AuthenticationResult failure(AuthenticationFailureReason reason, String message) {
        return new AuthenticationResult(false, null, null, message, reason, null);
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public AuthenticationFailureReason getFailureReason() {
        return failureReason;
    }

    public SecurityContext getSecurityContext() {
        return securityContext;
    }
}
