package com.globaldynamicssystems.aurum.identity.authorization;

public enum AuthorizationReason {
    AUTHORIZED,
    USER_NOT_FOUND,
    USER_DISABLED,
    USER_LOCKED,
    NO_ROLE,
    NO_PERMISSION,
    CAPABILITY_MISMATCH,
    RESOURCE_MISMATCH,
    ACTION_MISMATCH,
    SCOPE_MISMATCH,
    PERMISSION_DISABLED,
    INVALID_REQUEST
}