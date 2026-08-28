package com.globaldynamicssystems.aurum.identity.authorization;

public class AuthorizationResult {

    private AuthorizationDecision decision;
    private AuthorizationReason reason;
    private String message;
    private Long userId;
    private String permissionCode;

    public AuthorizationResult() {
    }

    public AuthorizationResult(AuthorizationDecision decision, AuthorizationReason reason, 
                               String message, Long userId, String permissionCode) {
        this.decision = decision;
        this.reason = reason;
        this.message = message;
        this.userId = userId;
        this.permissionCode = permissionCode;
    }

    public AuthorizationDecision getDecision() {
        return decision;
    }

    public void setDecision(AuthorizationDecision decision) {
        this.decision = decision;
    }

    public AuthorizationReason getReason() {
        return reason;
    }

    public void setReason(AuthorizationReason reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }
}