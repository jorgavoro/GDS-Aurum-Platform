package com.globaldynamicssystems.aurum.identity.authentication;

import java.util.UUID;

public interface LocalCredentialService {

    void setPassword(UUID userId, String rawPassword);

    void changePassword(UUID userId, String currentPassword, String newPassword);

    boolean hasLocalCredentials(UUID userId);
}
