package com.globaldynamicssystems.aurum.identity.authentication;

public enum AuthenticationFailureReason {
    INVALID_CREDENTIALS,
    USER_NOT_FOUND,
    USER_DISABLED,
    USER_LOCKED,
    USER_PENDING,
    USER_DELETED,
    LOCAL_AUTHENTICATION_NOT_ENABLED,
    INVALID_REQUEST
}
